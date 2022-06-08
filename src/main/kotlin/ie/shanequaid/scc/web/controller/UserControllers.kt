package ie.shanequaid.scc.web.controller

import ie.shanequaid.scc.persistence.model.BookingRequest
import ie.shanequaid.scc.persistence.model.BookingStatus
import ie.shanequaid.scc.persistence.model.dto.BookingRequestDTO
import ie.shanequaid.scc.persistence.model.dto.UserDTO
import ie.shanequaid.scc.persistence.model.dto.UserManagementDTO
import ie.shanequaid.scc.persistence.model.dto.toDto
import ie.shanequaid.scc.service.BookingRequestService
import ie.shanequaid.scc.service.UserService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.time.LocalDate
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

@RestController
@RequestMapping
class UserBookingRequestController(private val service: BookingRequestService) {
    private val logger = KotlinLogging.logger {}

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/private/booking")
    fun create(@RequestBody dto: BookingRequestDTO, principal: Principal) {
        val userGuid = UUID.fromString(principal.name)
        dto.status = BookingStatus.WAITING_COLLECTION
        val savedEntity = service.create(dto, userGuid)
        logger.info { "Booking created with id:${savedEntity.id}" }
    }

    @GetMapping("/api/private/bookings")
    fun findAllForUser(principal: Principal): Collection<BookingRequestDTO> {
        val userGuid = UUID.fromString(principal.name)
        val orders: Iterable<BookingRequest> = service.findAllByUser(userGuid, LocalDate.now().minusYears(1))
        return orders.map { it.toDto() }
    }
}