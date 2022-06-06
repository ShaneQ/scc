package ie.shanequaid.scc.service

import ie.shanequaid.scc.exceptions.ResourceNotFoundException
import ie.shanequaid.scc.persistence.model.User
import ie.shanequaid.scc.persistence.model.UserStatus
import ie.shanequaid.scc.persistence.model.dto.KeycloakUserInfo
import ie.shanequaid.scc.persistence.model.dto.Role
import ie.shanequaid.scc.persistence.model.dto.UserDTO
import ie.shanequaid.scc.persistence.model.dto.UserManagementDTO
import ie.shanequaid.scc.persistence.model.dto.toEntity
import ie.shanequaid.scc.persistence.model.dto.toNewUserEntity
import ie.shanequaid.scc.persistence.repository.IUserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService constructor(
    private val repository: IUserRepository,
    private val keycloakService: KeycloakService,
    private val emailService: EmailService,
) {
    fun create(dto: UserDTO, userId: UUID): User {
        val entity = dto.toNewUserEntity(userId)
        val user: User = repository.save(entity)
        keycloakService.addRole(userId, Role.SCC_USER_ROLE)
        emailService.sendEmailActivatedAdmin(user.fullName, dto.email)
        return user
    }

    fun update(dto: UserDTO, userId: UUID): User {
        val user: User = get(userId)
        val entity: User = dto.toEntity(userId, user.startDate, user.endDate, user.membership, user.status)
        return repository.save(entity)
    }

    fun get(id: UUID): User {
        return repository.findById(id) ?: throw ResourceNotFoundException("No Personal Info found")
    }

    fun findAll(): Iterable<User> {
        return repository.findAll()
    }

    fun getKeycloakInfo(keycloakUserId: UUID): KeycloakUserInfo {
        return keycloakService.getUserInfo(keycloakUserId)
    }

    fun updateUserSettings(userId: UUID, dto: UserManagementDTO): User {
        val user: User = get(userId)
        keycloakService.addRole(userId, Role.SCC_USER_ROLE)
        when (dto.status) {
            UserStatus.ACTIVATED -> if (dto.membership == 1 || dto.membership == 2) {
                try {
                    keycloakService.addRole(userId, Role.SCC_ACTIVE_MEMBERSHIP)
                } catch (ex: Exception) {
/*
                    log.error("Exception occurred when communicating with keycloak via the client", ex)
*/
                    throw ex
                }
                emailService.sendEmailActivatedUser(user.firstName, userId, user.email)
            }
            UserStatus.REQUESTED, UserStatus.BLOCKED, UserStatus.DEACTIVATED -> keycloakService.removeRole(
                userId,
                Role.SCC_ACTIVE_MEMBERSHIP
            )
        }
        user.membership = dto.membership
        user.endDate = dto.endDate
        user.startDate = dto.startDate
        user.status = UserStatus.valueOf(dto.status.name)
        return repository.save(user)
    }
}
