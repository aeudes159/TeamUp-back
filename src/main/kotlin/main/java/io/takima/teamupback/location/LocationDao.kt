package main.java.io.takima.teamupback.location

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import java.math.BigDecimal
import org.springframework.stereotype.Component

@Component
class LocationDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
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

    fun findAllWithPagination(offset: Int, limit: Int): List<Location> {
        return entityManager.createQuery(
            "SELECT l FROM Location l ORDER BY l.name",
            Location::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(l) FROM Location l",
            Long::class.javaObjectType
        ).singleResult
    }

    fun searchByName(name: String, offset: Int, limit: Int): List<Location> {
        return entityManager.createQuery(
            "SELECT l FROM Location l WHERE LOWER(l.name) LIKE LOWER(:name) ORDER BY l.name",
            Location::class.java
        )
            .setParameter("name", "%$name%")
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }
}
