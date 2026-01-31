package main.java.io.takima.teamupback.rating

import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * RatingDao - Extends BaseDao for common pagination methods.
 * Custom methods for finding ratings by location and computing averages.
 */
@Component
class RatingDao : BaseDao<Rating>(Rating::class) {

    override fun getDefaultOrderBy(): String = "e.createdAt DESC"

    /**
     * Find ratings by location ID with pagination
     */
    fun findByLocationIdWithPagination(locationId: Int, offset: Int, limit: Int): List<Rating> {
        return findWithPagination(
            whereClause = "e.location.id = :locationId",
            parameters = mapOf("locationId" to locationId),
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count ratings for a location
     */
    fun countByLocationId(locationId: Int): Long {
        return countWhere("e.location.id = :locationId", mapOf("locationId" to locationId))
    }

    /**
     * Get the average rating for a location
     */
    fun getAverageRatingForLocation(locationId: Int): Double? {
        return entityManager.createQuery(
            "SELECT AVG(e.ratingValue) FROM Rating e WHERE e.location.id = :locationId",
            Double::class.javaObjectType
        )
            .setParameter("locationId", locationId)
            .singleResult
    }
}
