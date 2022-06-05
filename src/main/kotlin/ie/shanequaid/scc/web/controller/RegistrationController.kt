package ie.shanequaid.scc.web.controller

import ie.shanequaid.scc.persistence.model.dto.KeycloakUserInfo
import ie.shanequaid.scc.persistence.model.dto.UserDTO
import ie.shanequaid.scc.service.UserService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.UUID

@RestController
@RequestMapping("/api/registration")
class RegistrationController(private val service: UserService) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/user/info")
    fun getUserInfo(principal: Principal): KeycloakUserInfo {
        val keycloakUserId = UUID.fromString(principal.name)
        return service.getKeycloakInfo(keycloakUserId)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/personal")
    fun create(@RequestBody dto: UserDTO, principal: Principal) {
        val userGuid = UUID.fromString(principal.name)

        val savedEntity = service.create(dto, userGuid)
        logger.info { "Personal info created for User: ${savedEntity.id}" }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/personal/exists")
    fun exists(principal: Principal) {
        val userGuid = UUID.fromString(principal.name)
        logger.info { "Check user registered id: $userGuid " }

        service.get(userGuid)
    }
}