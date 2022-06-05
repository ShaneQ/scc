package ie.shanequaid.scc.web.controller

import ie.shanequaid.scc.persistence.model.dto.UserDTO
import ie.shanequaid.scc.persistence.model.dto.toDto
import ie.shanequaid.scc.service.UserService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.UUID

@RestController
@RequestMapping(value = ["/api"])
class UserController(
    private val service: UserService
) {
    private val logger = KotlinLogging.logger {}

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/private/personal/")
    fun update(@RequestBody dto: UserDTO, principal: Principal) {
        val userId = UUID.fromString(principal.name)
        val savedEntity = service.update(dto, userId)
        logger.info { "Personal Info Updated with id:${savedEntity.id}" }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/private/personal")
    operator fun get(principal: Principal): UserDTO {
        val keycloakUserId = UUID.fromString(principal.name)
        logger.info { "Personal info request for User: $keycloakUserId" }

        return service.get(keycloakUserId).toDto()
    }
}