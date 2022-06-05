package ie.shanequaid.scc.persistence.model

import org.hibernate.annotations.Type
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity(name = "BOOKING_REQUEST")
class BookingRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @JoinColumn(name = "id_product_inventory")
    @ManyToOne(fetch = FetchType.LAZY)
    var productInventory: ProductInventory,

    @JoinColumn(name = "id_product")
    @ManyToOne(fetch = FetchType.LAZY)
    var product: Product,

    @OneToOne
    @JoinColumn(name = "id_user")
    @Type(type = "uuid-char")
    var user: User,
    var startDate: LocalDate,
    var collectionPlace: String,

    @Enumerated(EnumType.STRING)
    var status: BookingStatus

)

enum class BookingStatus {
    WAITING_COLLECTION, ACTIVE, WAITING_RETURN, COMPLETE, LATE_RETURN
}
