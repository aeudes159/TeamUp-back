package main.java.io.takima.teamupback.location

import jakarta.persistence.EntityManager
import java.math.BigDecimal
import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * LocationDao - Extends BaseDao for common pagination methods.
 * Custom methods for complex filtering and search.
 */
@Component
class LocationDao : BaseDao<Location>(Location::class) {

    override fun getDefaultOrderBy(): String = "e.name ASC"

    /**
     * Search locations by name (case-insensitive)
     */
    fun searchByName(name: String, offset: Int, limit: Int): List<Location> {
        return searchByFieldWithPagination(
            fieldName = "name",
            searchValue = name,
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count locations matching a search term
     */
    fun countByName(name: String): Long {
        return countWhere(
            "LOWER(e.name) LIKE LOWER(:search)",
            mapOf("search" to "%$name%")
        )
    }

    /**
     * Find locations with advanced filters (name, price range, sorting).
     * This method has complex filter logic that can't use BaseDao helpers.
     */
    fun findAllWithFilters(
        offset: Int,
        limit: Int,
        name: String?,
        minPrice: BigDecimal?,
        maxPrice: BigDecimal?,
        sort: String
    ): List<Location> {
        val orderBy = when (sort.uppercase()) {
            "POPULARITY" -> "AVG(r.ratingValue) DESC"
            else -> "l.name ASC"
        }

        val query = StringBuilder("""
            SELECT l
            FROM Location l
            LEFT JOIN Rating r ON r.location = l
            WHERE 1=1
        """)

        if (!name.isNullOrBlank()) {
            query.append(" AND LOWER(l.name) LIKE LOWER(:name)")
        }
        if (minPrice != null) {
            query.append(" AND l.averagePrice >= :minPrice")
        }
        if (maxPrice != null) {
            query.append(" AND l.averagePrice <= :maxPrice")
        }

        query.append("""
            GROUP BY l.id
            ORDER BY $orderBy
        """)

        val q = entityManager.createQuery(query.toString(), Location::class.java)

        name?.let { q.setParameter("name", "%$it%") }
        minPrice?.let { q.setParameter("minPrice", it) }
        maxPrice?.let { q.setParameter("maxPrice", it) }

        return q
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    /**
     * Count locations matching filters
     */
    fun countWithFilters(
        name: String?,
        minPrice: BigDecimal?,
        maxPrice: BigDecimal?
    ): Long {
        val query = StringBuilder("""
            SELECT COUNT(l)
            FROM Location l
            WHERE 1=1
        """)

        if (!name.isNullOrBlank()) {
            query.append(" AND LOWER(l.name) LIKE LOWER(:name)")
        }
        if (minPrice != null) {
            query.append(" AND l.averagePrice >= :minPrice")
        }
        if (maxPrice != null) {
            query.append(" AND l.averagePrice <= :maxPrice")
        }

        val q = entityManager.createQuery(query.toString(), java.lang.Long::class.java)

        name?.let { q.setParameter("name", "%$it%") }
        minPrice?.let { q.setParameter("minPrice", it) }
        maxPrice?.let { q.setParameter("maxPrice", it) }

        return q.singleResult.toLong()
    }
}
