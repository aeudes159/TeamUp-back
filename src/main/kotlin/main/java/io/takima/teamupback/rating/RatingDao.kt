package main.java.io.takima.teamupback.rating

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class RatingDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<Rating> {
        return entityManager.createQuery(
            "SELECT r FROM Rating r ORDER BY r.createdAt DESC",
            Rating::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(r) FROM Rating r",
            Long::class.javaObjectType
        ).singleResult
    }

    fun findByLocationIdWithPagination(locationId: Int, offset: Int, limit: Int): List<Rating> {
        return entityManager.createQuery(
            "SELECT r FROM Rating r WHERE r.location.id = :locationId ORDER BY r.createdAt DESC",
            Rating::class.java
        )
            .setParameter("locationId", locationId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun getAverageRatingForLocation(locationId: Int): Double? {
        return entityManager.createQuery(
            "SELECT AVG(r.ratingValue) FROM Rating r WHERE r.location.id = :locationId",
            Double::class.javaObjectType
        )
            .setParameter("locationId", locationId)
            .singleResult
    }
}
