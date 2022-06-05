package ie.shanequaid.scc.service

import ie.shanequaid.scc.exceptions.ResourceNotFoundException
import ie.shanequaid.scc.persistence.model.BookingRequest
import ie.shanequaid.scc.persistence.model.BookingStatus
import ie.shanequaid.scc.persistence.model.dto.BookingRequestDTO
import ie.shanequaid.scc.persistence.model.dto.toEntity
import ie.shanequaid.scc.persistence.repository.IBookingRequestRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class BookingRequestService constructor(
    private val repository: IBookingRequestRepository,
    private val productService: ProductService,
    private val userService: UserService,
    private val emailService: EmailService,

    ) {

    fun create(dto: BookingRequestDTO, userId: UUID): BookingRequest {
        val product = productService.findById(dto.productId) ?: throw ResourceNotFoundException("Product not found")
        val user = userService.get(userId)
        val entity = dto.toEntity(user, product, BookingStatus.WAITING_COLLECTION)
        val result: BookingRequest = repository.save(entity)

        emailService.sendEmailBookingAdmin(result)

        return result
    }

    fun findAllByUser(userId: UUID, fromDate: LocalDate): List<BookingRequest> {
        return repository.findAllByUserIdAndStartDateGreaterThan(userId, fromDate)
            ?: throw ResourceNotFoundException("Bookings not found")
    }

    fun findAllByProduct(productId: Long): List<BookingRequest> {
        return repository.findAllByProductId(productId) ?: throw ResourceNotFoundException("Bookings not found")
    }

    fun findAll(): MutableIterable<BookingRequest> {
        return repository.findAll()
    }

    fun get(id: Long): BookingRequest {
        return repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("Bookings not found")
    }

    fun updateStatus(id: Long, status: BookingStatus) {
        val bookingRequest: BookingRequest = get(id)
        bookingRequest.status = status
/*
        log.info("Booking {} updated with status {} for user {}", id, status, bookingRequest.getUser().getId())
*/
        repository.save(bookingRequest)
    }
}