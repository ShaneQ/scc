package ie.shanequaid.scc.persistence.model.dto

import ie.shanequaid.scc.persistence.model.BookingStatus
import java.time.LocalDate
import java.util.UUID

data class BookingRequestDTO(
    val id: Long? = null,
    val productId: Long,
    val productName: String? = null,
    val userId: UUID? = null,
    val userName: String? = null,
    val coverImg: ImageDTO? = null,
    val productSize: ProductInventoryTwoDTO,
    val startDate: LocalDate,
    val returnDate: LocalDate? = null,
    val collectionPlace: String,
    var status: BookingStatus? = null
)

data class ImageDTO(
    val id: Long,
    val url: String
)

data class KeycloakUserInfo(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String
)

data class SizeDTO(
    val id: Long,
    val name: String
)

