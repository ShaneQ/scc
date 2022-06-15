package ie.shanequaid.scc.service

import ie.shanequaid.scc.exceptions.ResourceNotFoundException
import ie.shanequaid.scc.persistence.model.Product
import ie.shanequaid.scc.persistence.model.ProductInventory
import ie.shanequaid.scc.persistence.model.ProductInventoryStatus
import ie.shanequaid.scc.persistence.model.dto.Filters
import ie.shanequaid.scc.persistence.model.dto.ProductDTO
import ie.shanequaid.scc.persistence.model.dto.toEntity
import ie.shanequaid.scc.persistence.repository.IProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(private val repository: IProductRepository) {
    fun findById(id: Long): Product? {
        return repository.findByIdOrNull(id)
    }

    fun update(productDTO: ProductDTO): Product {
        val entity = findById(productDTO.id) ?: throw ResourceNotFoundException("Product not found")
        val newEntity = productDTO.toEntity()
        val newSizes = productDTO.sizes.filter { it.id == null }.map { it.toEntity(productDTO.id) }
        newEntity.sizes = entity.sizes.plus(newSizes)
        return repository.save(newEntity)
    }

    fun create(productDTO: ProductDTO): Product {
        val newEntity: Product = productDTO.toEntity()
        newEntity.measurement = productDTO.measurements.toEntity(newEntity.id!!, newEntity)
        newEntity.sizes = productDTO.sizes.map { it.toEntity(productDTO.id) }
        newEntity.active = false
        return repository.save(newEntity)
    }

    fun findAll(): List<Product> {
        return repository.findByDeletedFalse()
    }

    fun findAllActive(): List<Product> {
        return repository.findByDeletedFalseAndActiveTrue()
    }

    fun toggleHidden(id: Long, hidden: Boolean) {
        val entity = findById(id) ?: throw ResourceNotFoundException("Product not found")
        entity.active = !hidden
        repository.save(entity)
    }

    fun delete(id: Long) {
        val entity = findById(id) ?: throw ResourceNotFoundException("Product not found")
        entity.deleted = true
        repository.save(entity)
    }

    fun findAllWithFilters(filters: Map<String, String>): List<Product> {
        return findAllActive()
            .asSequence()
            .filter { Filters.filterBySize(it, filters) }
            .filter { Filters.filterByColor(it, filters) }
            .filter { Filters.filterBySeason(it, filters) }
            .filter { Filters.filterByCategory(it, filters) }
            .filter { Filters.filterByName(it, filters) }
            .toList()
    }

    fun updateInventoryStatus(productId: Long, inventoryId: Long, status: ProductInventoryStatus) {
        val entity = findById(productId) ?: throw ResourceNotFoundException("Product not found")
        entity.sizes.map {
            if (it.id == inventoryId) {
                ProductInventory(status = status, id = it.id, id_product = it.id_product, size = it.size)
            }
        }
        repository.save(entity)
    }
}