package ie.shanequaid.scc.persistence.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.PrimaryKeyJoinColumn

@Entity(name = "PRODUCT")
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    private val name: String? = null
    private val dryClean = false
    private val hidden = false
    private val deleted = false
    private val quickDesc: String? = null
    private val brand: String? = null
    private val material: String? = null
    private val description: String? = null
    private val fittingInfo: String? = null
    private val washInfo: String? = null
    private val retailPrice = 0.0

    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "id_product_category")
    private val category: ProductCategory? = null

    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "id_season")
    private val season: Season? = null

    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "id_color")
    private val color: Color? = null

    @OneToOne(mappedBy = "product", cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn
    private val measurement: ProductMeasurement? = null

    @OneToOne
    @JoinColumn(name = "id_cover_img")
    private val imgCover: Image? = null

    @OneToMany(cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "product_image",
        joinColumns = [JoinColumn(name = "ID_PRODUCT", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "ID_IMAGE", referencedColumnName = "id")]
    )
    private val images: List<Image>? = null

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_product")
    private val occasions: List<ProductOccasion>? = null

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_product")
    private val sizes: List<ProductInventory>? = null
}
