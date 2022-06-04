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
class BookingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @JoinColumn(name = "id_product_inventory")
    @ManyToOne(fetch = FetchType.LAZY)
    private val productInventory: ProductInventory? = null

    @JoinColumn(name = "id_product")
    @ManyToOne(fetch = FetchType.LAZY)
    private val product: Product? = null

    @OneToOne
    @JoinColumn(name = "id_user")
    @Type(type = "uuid-char")
    private val user: User? = null
    private val startDate: LocalDate? = null
    private val collectionPlace: String? = null

    @Enumerated(EnumType.STRING)
    private val status: Status? = null

    enum class Status {
        WAITING_COLLECTION, ACTIVE, WAITING_RETURN, COMPLETE, LATE_RETURN
    }
}


