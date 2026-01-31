package main.java.io.takima.teamupback.message

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class MessageDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<Message> {
        return entityManager.createQuery(
            "SELECT m FROM Message m ORDER BY m.sentAt DESC",
            Message::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(m) FROM Message m",
            Long::class.javaObjectType
        ).singleResult
    }

    fun findByDiscussionIdWithPagination(discussionId: Int, offset: Int, limit: Int): List<Message> {
        return entityManager.createQuery(
            "SELECT m FROM Message m WHERE m.discussion.id = :discussionId ORDER BY m.sentAt DESC",
            Message::class.java
        )
            .setParameter("discussionId", discussionId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }
}
