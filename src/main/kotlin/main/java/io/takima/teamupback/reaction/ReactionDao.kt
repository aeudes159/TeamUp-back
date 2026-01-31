package main.java.io.takima.teamupback.reaction

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class ReactionDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<Reaction> {
        return entityManager.createQuery(
            "SELECT r FROM Reaction r ORDER BY r.createdAt DESC",
            Reaction::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(r) FROM Reaction r",
            Long::class.javaObjectType
        ).singleResult
    }

    fun countByMessageId(messageId: Int): Long {
        return entityManager.createQuery(
            "SELECT COUNT(r) FROM Reaction r WHERE r.messageId = :messageId",
            Long::class.javaObjectType
        )
            .setParameter("messageId", messageId)
            .singleResult
    }

    fun findByMessageIdWithPagination(messageId: Int, offset: Int, limit: Int): List<Reaction> {
        return entityManager.createQuery(
            "SELECT r FROM Reaction r WHERE r.messageId = :messageId ORDER BY r.createdAt DESC",
            Reaction::class.java
        )
            .setParameter("messageId", messageId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun findByUserIdWithPagination(userId: Int, offset: Int, limit: Int): List<Reaction> {
        return entityManager.createQuery(
            "SELECT r FROM Reaction r WHERE r.userId = :userId ORDER BY r.createdAt DESC",
            Reaction::class.java
        )
            .setParameter("userId", userId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun countByUserId(userId: Int): Long {
        return entityManager.createQuery(
            "SELECT COUNT(r) FROM Reaction r WHERE r.userId = :userId",
            Long::class.javaObjectType
        )
            .setParameter("userId", userId)
            .singleResult
    }
}
