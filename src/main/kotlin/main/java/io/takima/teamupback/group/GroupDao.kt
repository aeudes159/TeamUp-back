package main.java.io.takima.teamupback.group

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class GroupDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<Group> {
        return entityManager.createQuery(
            "SELECT g FROM Group g ORDER BY g.createdAt DESC",
            Group::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(g) FROM Group g",
            Long::class.javaObjectType
        ).singleResult
    }

    fun findPublicGroups(offset: Int, limit: Int): List<Group> {
        return entityManager.createQuery(
            "SELECT g FROM Group g WHERE g.isPublic = true ORDER BY g.createdAt DESC",
            Group::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }
}
