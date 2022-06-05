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
class User constructor(
    @Id
    @Type(type = "uuid-char")
    var id: UUID? = null,
    var firstName: String,
    var lastName: String,
    var phone: String,
    var email: String,
    var dob: Date,
    var country: String,
    var city: String,

    @Column(name = "eircode")
    var eirCode: String,
    var addressLineOne: String,
    var addressLineTwo: String,
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null,
    @Column(insertable=false, updatable=false)
    var createdAt: LocalDate? = null,
    var membership: Int? = null,

    @Enumerated(EnumType.STRING)
    var status: UserStatus,

    ) {
    val fullName: String
        get() = java.lang.String.format("%s %s", firstName, lastName)
}

enum class UserStatus {
    REQUESTED, ACTIVATED, DEACTIVATED, BLOCKED
}

