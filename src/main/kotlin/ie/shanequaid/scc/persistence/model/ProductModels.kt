package ie.shanequaid.scc.persistence.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.MapsId
import javax.persistence.OneToOne

@Entity
class ProductInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    private val id_product: Long? = null

    @OneToOne
    @JoinColumn(name = "id_size")
    private val size: Size? = null
    private val status: String? = null
}

enum class ProductInventoryStatus {
    BOOKED, IN_USE, WAITING_RETURN, STORED, WASH, DEACTIVATED, LATE_RETURN
}

@Entity
class ProductMeasurement : Serializable {
    @Id
    @Column(name = "id_product")
    private val id: Long? = null

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_product")
    private val product: Product? = null
    private val length: String? = null
    private val chest: String? = null
    private val hips: String? = null
    private val waist: String? = null
}

@Entity
class ProductOccasion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    private val id_product: Long? = null

    @OneToOne
    @JoinColumn(name = "id_occasion")
    private val occasion: Occasion? = null
}
@Entity
class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    private val name: String? = null
}