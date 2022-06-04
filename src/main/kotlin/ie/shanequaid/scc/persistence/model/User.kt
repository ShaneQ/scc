package ie.shanequaid.scc.persistence.model

import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.Date
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity(name = "USER")
class User {
    @Id
    @Type(type = "uuid-char")
    private val id: UUID? = null
    private val firstName: String? = null
    private val lastName: String? = null
    private val phone: String? = null
    private val email: String? = null
    private val dob: Date? = null
    private val country: String? = null
    private val city: String? = null

    @Column(name = "eircode")
    private val eirCode: String? = null
    private val addressLineOne: String? = null
    private val addressLineTwo: String? = null
    private val startDate: LocalDate? = null
    private val endDate: LocalDate? = null
    private val createdAt: LocalDate? = null
    private val membership = 0

    @Enumerated(EnumType.STRING)
    private val status: Status? = null

    enum class Status {
        REQUESTED, ACTIVATED, DEACTIVATED, BLOCKED
    }

    val fullName: String
        get() = java.lang.String.format("%s %s", firstName, lastName)
}


