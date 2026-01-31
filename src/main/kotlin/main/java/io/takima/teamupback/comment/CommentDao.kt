package main.java.io.takima.teamupback.comment

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class CommentDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<Comment> {
        return entityManager.createQuery(
            "SELECT c FROM Comment c ORDER BY c.createdAt DESC",
            Comment::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(c) FROM Comment c",
            Long::class.javaObjectType
        ).singleResult
    }

    fun findByPostIdWithPagination(postId: Int, offset: Int, limit: Int): List<Comment> {
        return entityManager.createQuery(
            "SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.createdAt ASC",
            Comment::class.java
        )
            .setParameter("postId", postId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun countByPostId(postId: Int): Long {
        return entityManager.createQuery(
            "SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId",
            Long::class.javaObjectType
        )
            .setParameter("postId", postId)
            .singleResult
    }
}
