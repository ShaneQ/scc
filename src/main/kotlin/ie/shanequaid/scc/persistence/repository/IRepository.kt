package ie.shanequaid.scc.persistence.repository

import ie.shanequaid.scc.persistence.model.BookingRequest
import ie.shanequaid.scc.persistence.model.Image
import ie.shanequaid.scc.persistence.model.Product
import ie.shanequaid.scc.persistence.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.Optional
import java.util.UUID

interface IBookingRequestRepository : PagingAndSortingRepository<BookingRequest?, Long?> {
    fun findAllByUserIdAndStartDateGreaterThan(userId: UUID?, startDate: LocalDate?): List<BookingRequest?>?

    @Query("SELECT a from BOOKING_REQUEST a  left join ProductInventory b on a.productInventory.id=b.id where b.id_product = :productId")
    fun findAllByProductId(@Param("productId") productId: Long): List<BookingRequest?>?
}

interface IImageRepository : PagingAndSortingRepository<Image?, Long?>

interface IProductRepository : PagingAndSortingRepository<Product?, Long?> {
    fun findByDeletedFalseAndHiddenFalse(): List<Product?>?
    fun findByDeletedFalse(): List<Product?>?
}

interface IUserRepository : PagingAndSortingRepository<User?, Long?> {
    fun findById(fromString: UUID?): Optional<User?>?
}
