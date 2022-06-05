package ie.shanequaid.scc.web.controller

import ie.shanequaid.scc.persistence.model.BookingRequest
import ie.shanequaid.scc.persistence.model.BookingStatus
import ie.shanequaid.scc.persistence.model.dto.BookingRequestDTO
import ie.shanequaid.scc.persistence.model.dto.toDto
import ie.shanequaid.scc.service.BookingRequestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.UUID

@RequestMapping("/api/admin")
@RestController
class BookingManagementController(
    private val service: BookingRequestService
) {
    @GetMapping("/bookings")
    fun findAll(): Collection<BookingRequestDTO> {
        val entities: Iterable<BookingRequest> = service.findAll()

        return entities.map {
            it.toDto()
        }
    }

    @GetMapping("/bookings/user/{userGuid}")
    fun findAllAdminForUser(@PathVariable userGuid: UUID): Collection<BookingRequestDTO> {
        val orders: List<BookingRequest> = service.findAllByUser(userGuid, LocalDate.now().minusYears(1))
        return orders.map {
            it.toDto()
        }
    }

    @GetMapping("/bookings/product/{productId}")
    fun findAllAdminForProduct(@PathVariable productId: Long): Collection<BookingRequestDTO> {
        val orders: Iterable<BookingRequest> = service.findAllByProduct(productId)
        return orders.map {
            it.toDto()
        }
    }

    @PostMapping("/booking/{id}/status/{status}")
    fun updateBookingStatus(@PathVariable id: Long, @PathVariable status: BookingStatus) {
        service.updateStatus(id, status)
    }
}