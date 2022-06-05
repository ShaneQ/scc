package ie.shanequaid.scc.web.controller

import ie.shanequaid.scc.persistence.model.dto.UserDTO
import ie.shanequaid.scc.persistence.model.dto.UserManagementDTO
import ie.shanequaid.scc.persistence.model.dto.toDto
import ie.shanequaid.scc.service.UserService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(value = ["/api/admin"])
class UserManagementController(private val service: UserService) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/users")
    fun findAll(): Collection<UserDTO> {
        val users = service.findAll()
        return users.map { it.toDto() }
    }

    @GetMapping("/user/{id}")
    fun find(@PathVariable id: UUID): UserDTO {
        val user = service.get(id)
        return user.toDto()
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/users/user/{userId}")
    fun updateUserSettings(@PathVariable userId: UUID, @RequestBody dto: UserManagementDTO): UserDTO {
        logger.info { "Admin updated user settings id: $userId" }
        return service.updateUserSettings(userId, dto).toDto()
    }
}