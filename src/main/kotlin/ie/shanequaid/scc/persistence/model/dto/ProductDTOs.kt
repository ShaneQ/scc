package ie.shanequaid.scc.persistence.model.dto

import ie.shanequaid.scc.persistence.model.ProductInventoryStatus

data class ProductDTO constructor(
    val id: Long,
    val name: String,
    val quickDesc: String,
    val brand: String,
    val fittingInfo: String,
    val washInfo: String,
    val material: String,
    val description: String,
    val productCategory: Number,
    val season: Number,
    val color: Number,
    val retailPrice: Number,
    val dryClean: Boolean,
    val hidden: Boolean,
    val measurements: ProductMeasurementDTO,
    val imgCover: ImageDTO,
    val images: List<ImageDTO>,
    val sizes: List<ProductInventoryTwoDTO>
)

data class ProductInventoryDTO constructor(
    val product: ProductDTO,
    val status: ProductInventoryStatus,
    val id: Long,
    val id_size: Long
)

data class ProductMeasurementDTO(
    val length: String,
    val chest: String,
    val hips: String,
    val waist: String
)

data class ProductInventoryTwoDTO(
    val id: Long? = null,
    val id_size: Long? = null,
    val status: ProductInventoryStatus? = null
)

enum class Role {
    SCC_USER_ROLE, SCC_ADMIN_ROLE, SCC_ACTIVE_MEMBERSHIP
}

data class RoleDTO (
    var id: String,
    var name: String
)