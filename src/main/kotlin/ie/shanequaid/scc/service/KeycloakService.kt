package ie.shanequaid.scc.service

import ie.shanequaid.scc.persistence.model.dto.KeycloakUserInfo
import ie.shanequaid.scc.persistence.model.dto.Role
import ie.shanequaid.scc.spring.properties.KeycloakClientProperties
import mu.KotlinLogging
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class KeycloakService(
    private val keycloakClient: Keycloak,
    private val keycloakClientProperties: KeycloakClientProperties
) {
    private val logger = KotlinLogging.logger {}

    companion object {
        private const val SECOND_CLOSET_CLUB = "secondClosetClub"
    }

    fun addRole(userId: UUID, role: Role) {
        val roleName = role.name.lowercase()
        val hasRole = keycloakClient.realm(SECOND_CLOSET_CLUB).users()[userId.toString()].roles()
            .clientLevel(keycloakClientProperties.clientId).listAll().stream()
            .anyMatch { roleRep: RoleRepresentation ->
                roleRep.name.equals(roleName, ignoreCase = true)
            }
        if (!hasRole) {
            logger.info { "Added role $roleName to user $userId" }
            keycloakClient.realm(SECOND_CLOSET_CLUB).users()[userId.toString()].roles()
                .clientLevel(keycloakClientProperties.clientId).add(listOf(getKeycloakRoleFromKeycloak(role, userId)))
        }
    }

    private fun getKeycloakRoleFromKeycloak(role: Role, userId: UUID): RoleRepresentation {
        when (role) {
            Role.SCC_ACTIVE_MEMBERSHIP,
            Role.SCC_USER_ROLE ->
                return keycloakClient.realm(SECOND_CLOSET_CLUB).users()[userId.toString()].roles()
                    .clientLevel(keycloakClientProperties.clientId).listAvailable()
                    .first { it.name.equals(role.name.lowercase()) }
            else ->
                throw IllegalArgumentException("Role not created $role")
        }
    }

    fun removeRole(userId: UUID, role: Role) {
        val roleName = role.name.lowercase()
        val hasRole = keycloakClient.realm(SECOND_CLOSET_CLUB).users()[userId.toString()].roles()
            .clientLevel(keycloakClientProperties.clientId).listAll().stream()
            .anyMatch { roleRep: RoleRepresentation ->
                roleRep.name.equals(roleName, ignoreCase = true)
            }
        if (hasRole) {
            logger.info { "Removed role $roleName to user $userId" }

            keycloakClient.realm(SECOND_CLOSET_CLUB).users()[userId.toString()].roles()
                .clientLevel(keycloakClientProperties.clientId).remove(listOf(getKeycloakRoleFromKeycloak(role, userId)))
        }
    }

    fun getUserInfo(userId: UUID): KeycloakUserInfo {
        val userRepresentation = keycloakClient.realm(SECOND_CLOSET_CLUB).users()[userId.toString()]
            .toRepresentation()
        return KeycloakUserInfo(
            userRepresentation.username,
            userRepresentation.firstName,
            userRepresentation.lastName,
            userRepresentation.email
        )
    }
}
