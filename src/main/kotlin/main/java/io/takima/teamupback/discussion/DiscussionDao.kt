package main.java.io.takima.teamupback.discussion

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class DiscussionDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<Discussion> {
        return entityManager.createQuery(
            "SELECT d FROM Discussion d ORDER BY d.createdAt DESC",
            Discussion::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(d) FROM Discussion d",
            Long::class.javaObjectType
        ).singleResult
    }

    fun findByGroupIdWithPagination(groupId: Int, offset: Int, limit: Int): List<Discussion> {
        return entityManager.createQuery(
            "SELECT d FROM Discussion d WHERE d.group.id = :groupId ORDER BY d.createdAt DESC",
            Discussion::class.java
        )
            .setParameter("groupId", groupId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }
}
