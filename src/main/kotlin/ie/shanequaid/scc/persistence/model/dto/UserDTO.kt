package ie.shanequaid.scc.persistence.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import ie.shanequaid.scc.persistence.model.UserStatus
import java.time.LocalDate
import java.util.Date
import java.util.UUID

data class UserDTO (
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: Date,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    val createdAt: LocalDate? =null,
    val country: String,
    val city: String,
    val eirCode: String,
    val addressLineOne: String,
    val addressLineTwo: String,
    val status: UserStatus? =null,
    val membership: Number? = null,

    @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    val startDate: LocalDate? =null,

    @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    val endDate: LocalDate? =null,
    val bookingAllowanceMonthly: Number? =null,
    val bookingAllowanceRemainingMonthly: Number? =null
){
}

class UserManagementDTO (
    val status: UserStatus,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    val startDate: LocalDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    val endDate: LocalDate,
    val membership: Int
)
