package main.java.io.takima.teamupback.activityfeed

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class ActivityFeedDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<ActivityFeed> {
        return entityManager.createQuery(
            "SELECT af FROM ActivityFeed af ORDER BY af.id",
            ActivityFeed::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(af) FROM ActivityFeed af",
            Long::class.javaObjectType
        ).singleResult
    }
}
