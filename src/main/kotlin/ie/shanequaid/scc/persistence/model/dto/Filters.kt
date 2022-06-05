package ie.shanequaid.scc.persistence.model.dto

import ie.shanequaid.scc.persistence.model.Color
import ie.shanequaid.scc.persistence.model.Product
import ie.shanequaid.scc.persistence.model.ProductCategory
import ie.shanequaid.scc.persistence.model.ProductInventory
import ie.shanequaid.scc.persistence.model.Season
import org.apache.logging.log4j.util.Strings
import java.util.Arrays
import java.util.Locale
import java.util.stream.Collectors

class Filters {

    companion object {
        const val FILTER_BY_SIZE = "filterBySize"
        const val FILTER_BY_COLOR = "filterByColor"
        const val FILTER_BY_SEASON = "filterBySeason"
        const val FILTER_BY_CATEGORY = "filterByCategory"
        const val FILTER_BY_NAME = "filterByName"

        fun filterBySize(product: Product, filters: Map<String, String>): Boolean {
            var result = true
            if (filters.containsKey(FILTER_BY_SIZE) && Strings.isNotBlank(filters[FILTER_BY_SIZE])) {
                result = Sizes.contains(
                    product.sizes, filters[FILTER_BY_SIZE]!!
                        .split(",").toTypedArray()
                )
            }
            return result
        }

        fun filterByColor(product: Product, filters: Map<String, String>): Boolean {
            var result = true
            if (filters.containsKey(FILTER_BY_COLOR) && Strings.isNotBlank(filters[FILTER_BY_COLOR])) {
                result = Colors.equals(
                    product.color, filters[FILTER_BY_COLOR]!!
                        .split(",").toTypedArray()
                )
            }
            return result
        }

        fun filterBySeason(product: Product, filters: Map<String, String>): Boolean {
            var result = true
            if (filters.containsKey(FILTER_BY_SEASON) && Strings.isNotBlank(filters[FILTER_BY_SEASON])) {
                result = Seasons.equals(
                    product.season, filters[FILTER_BY_SEASON]!!
                        .split(",").toTypedArray()
                )
            }
            return result
        }

        fun filterByCategory(product: Product, filters: Map<String, String>): Boolean {
            var result = true
            if (filters.containsKey(FILTER_BY_CATEGORY) && Strings.isNotBlank(filters[FILTER_BY_CATEGORY])) {
                result = Categories.equals(
                    product.category, filters[FILTER_BY_CATEGORY]!!
                        .split(",").toTypedArray()
                )
            }
            return result
        }

        fun filterByName(product: Product, filters: Map<String, String>): Boolean {
            var result = true
            if (filters.containsKey(FILTER_BY_NAME) && Strings.isNotBlank(filters[FILTER_BY_NAME])) {
                result = product.name.lowercase(Locale.ROOT).contains(
                    filters[FILTER_BY_NAME]!!.lowercase()
                )
            }
            return result
        }
    }
}

object Categories {
    fun equals(category: ProductCategory, filters: Array<String>): Boolean {
        return Arrays.stream(filters).anyMatch(category.id.toString()::equals)
    }
}

object Colors {
    fun equals(color: Color, filters: Array<String>): Boolean {
        return Arrays.stream(filters).anyMatch(color.id.toString()::equals)
    }
}

object Seasons {
    fun equals(season: Season, filters: Array<String>): Boolean {
        return Arrays.stream(filters).anyMatch(season.id.toString()::equals)
    }
}

object Sizes {
    fun contains(sizes: List<ProductInventory>, filters: Array<String>?): Boolean {
        return sizes.stream().filter { productSize: ProductInventory ->
            Arrays.stream(
                filters
            ).anyMatch(productSize.size?.id.toString()::equals)
        }.collect(
            Collectors.toList<Any>()
        ).isNotEmpty()
    }
}