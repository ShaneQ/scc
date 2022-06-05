package ie.shanequaid.scc.persistence.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.PrimaryKeyJoinColumn

@Entity(name = "PRODUCT")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    val dryClean: Boolean = false,
    var hidden: Boolean = true,
    var deleted: Boolean = false,
    val quickDesc: String,
    val brand: String,
    val material: String,
    val description: String,
    val fittingInfo: String,
    val washInfo: String,
    val retailPrice: Double,

    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "id_product_category")
    val category: ProductCategory,

    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "id_season")
    val season: Season,

    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "id_color")
    val color: Color,

    @OneToOne(mappedBy = "product", cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn
    var measurement: ProductMeasurement,

    @OneToOne
    @JoinColumn(name = "id_cover_img")
    val imgCover: Image,

    @OneToMany(cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "product_image",
        joinColumns = [JoinColumn(name = "ID_PRODUCT", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "ID_IMAGE", referencedColumnName = "id")]
    )
    val images: List<Image>,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER )
    @JoinColumn(name = "id_product")
    var sizes: List<ProductInventory>
)
