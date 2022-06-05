package ie.shanequaid.scc.web.controller

import ie.shanequaid.scc.persistence.model.ProductInventoryStatus
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