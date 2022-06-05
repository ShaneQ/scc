package ie.shanequaid.scc.web.controller

import ie.shanequaid.scc.exceptions.ResourceNotFoundException
import ie.shanequaid.scc.persistence.model.dto.Filters.Companion.FILTER_BY_CATEGORY
import ie.shanequaid.scc.persistence.model.dto.Filters.Companion.FILTER_BY_COLOR
import ie.shanequaid.scc.persistence.model.dto.Filters.Companion.FILTER_BY_NAME
import ie.shanequaid.scc.persistence.model.dto.Filters.Companion.FILTER_BY_SEASON
import ie.shanequaid.scc.persistence.model.dto.Filters.Companion.FILTER_BY_SIZE
import ie.shanequaid.scc.persistence.model.dto.ProductDTO
import ie.shanequaid.scc.persistence.model.dto.toDto
import ie.shanequaid.scc.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.Objects
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