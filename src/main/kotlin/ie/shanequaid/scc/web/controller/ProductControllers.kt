package ie.shanequaid.scc.web.controller

import ie.shanequaid.scc.exceptions.ResourceNotFoundException
import ie.shanequaid.scc.persistence.model.ProductInventoryStatus
import ie.shanequaid.scc.persistence.model.dto.Filters.Companion.FILTER_BY_CATEGORY
import ie.shanequaid.scc.persistence.model.dto.Filters.Companion.FILTER_BY_COLOR
import ie.shanequaid.scc.persistence.model.dto.Filters.Companion.FILTER_BY_NAME
import ie.shanequaid.scc.persistence.model.dto.Filters.Companion.FILTER_BY_SEASON
import ie.shanequaid.scc.persistence.model.dto.Filters.Companion.FILTER_BY_SIZE
import ie.shanequaid.scc.persistence.model.dto.ProductDTO
import ie.shanequaid.scc.persistence.model.dto.ProductInventoryDTO
import ie.shanequaid.scc.persistence.model.dto.toDto
import ie.shanequaid.scc.service.ProductService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.QueryParam

@RestController
@RequestMapping(value = ["/api/public/product"])
class ProductController(private val productService: ProductService) {
    @GetMapping
    fun findAll(): Collection<ProductDTO> {
        return productService.findAllActive().map { it.toDto() }
    }

    @GetMapping("/query")
    fun search(
        @QueryParam(FILTER_BY_SIZE) filterBySize: String?,
        @QueryParam(FILTER_BY_COLOR) filterByColor: String?, @QueryParam(FILTER_BY_SEASON) filterBySeason: String?,
        @QueryParam(FILTER_BY_CATEGORY) filterByCategory: String?, @QueryParam(FILTER_BY_NAME) filterByName: String?
    ): Collection<ProductDTO> {
        val filters: MutableMap<String, String> = mutableMapOf()

        filterBySize?.let { filters[FILTER_BY_SIZE] = filterBySize }
        filterByColor?.let { filters[FILTER_BY_COLOR] = filterByColor }
        filterBySeason?.let { filters[FILTER_BY_SEASON] = filterBySeason }
        filterByCategory?.let { filters[FILTER_BY_CATEGORY] = filterByCategory }
        filterByColor?.let { filters[FILTER_BY_COLOR] = filterByColor }

        return productService.findAllWithFilters(filters).map { it.toDto() }
    }

    @GetMapping(value = ["/{id}"])
    fun findOne(@PathVariable id: Long): ProductDTO {
        val entity = productService.findById(id) ?: throw ResourceNotFoundException("Product not found")
        return entity.toDto()
    }
}

@RestController
@RequestMapping(value = ["/api/admin/product"])
class ProductAdminController(private val productService: ProductService) {

    private val logger = KotlinLogging.logger {}

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun create(@RequestBody dto: ProductDTO): Long {
        val savedProduct = productService.create(dto)

        logger.info { "Product Created with id:${savedProduct.id}" }
        return savedProduct.id!!
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    fun update(@RequestBody dto: ProductDTO): Long {
        val savedProduct = productService.update(dto)
        logger.info { "Product Updated with id:${savedProduct.id}" }

        return savedProduct.id!!
    }

    @GetMapping("/inventory")
    fun findAllInventory(): Collection<ProductInventoryDTO> {
        val products = productService.findAll()
        val inventory = mutableListOf<ProductInventoryDTO>()
        products.forEach { product -> product.sizes.forEach { inventory.add(it.toDto(product.toDto())) } }
        return inventory
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{productId}/inventory/{inventoryId}/{status}")
    fun updateInventoryStatus(
        @PathVariable productId: Long, @PathVariable inventoryId: Long,
        @PathVariable status: ProductInventoryStatus
    ) {
        logger.info { "Product Inventory Updated with inventory id:${inventoryId}, status $status" }
        productService.updateInventoryStatus(productId, inventoryId, status)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/hide/{id}")
    fun hide(@PathVariable id: Long) {
        logger.info { "Product Hidden with product id:${id}" }
        productService.toggleHidden(id, true)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/show/{id}")
    fun show(@PathVariable id: Long) {
        logger.info { "Product Shown with product id:${id}" }
        productService.toggleHidden(id, false)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        logger.info { "Product Deleted with product id:${id}" }
        productService.delete(id)
    }
}