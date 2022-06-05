package ie.shanequaid.scc.web.controller

import ie.shanequaid.scc.persistence.model.BookingRequest
import ie.shanequaid.scc.persistence.model.BookingStatus
import ie.shanequaid.scc.persistence.model.dto.BookingRequestDTO
import ie.shanequaid.scc.persistence.model.dto.toDto
import ie.shanequaid.scc.service.BookingRequestService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.time.LocalDate
import java.util.UUID

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