package ie.shanequaid.scc.persistence.model

import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.MapsId
import javax.persistence.OneToOne

@Entity
class ProductInventory(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(updatable = false, nullable = false)
    val guid: String? = UUID.randomUUID().toString(),

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    val status: ProductInventoryStatus? = null,

    val id_product: Long? = null,
    @OneToOne
    @JoinColumn(name = "id_size")
    val size: Size? = null,
    var active: Boolean = true
)

enum class ProductInventoryStatus {
    BOOKED, IN_USE, WAITING_RETURN, STORED, WASH, DEACTIVATED, LATE_RETURN
}

@Entity
class ProductMeasurement(
    @Id
    @Column(name = "id_product")
    val id: Long? = null,

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_product")
    val product: Product? = null,
    val length: String,
    val chest: String,
    val hips: String,
    val waist: String
)

@Entity
class ProductCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String? = null
)