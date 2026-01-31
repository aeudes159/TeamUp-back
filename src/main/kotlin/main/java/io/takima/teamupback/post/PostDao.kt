package main.java.io.takima.teamupback.post

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class PostDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<Post> {
        return entityManager.createQuery(
            "SELECT p FROM Post p ORDER BY p.postedAt DESC",
            Post::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(p) FROM Post p",
            Long::class.javaObjectType
        ).singleResult
    }

    fun findByAuthorIdWithPagination(authorId: Int, offset: Int, limit: Int): List<Post> {
        return entityManager.createQuery(
            "SELECT p FROM Post p WHERE p.authorId = :authorId ORDER BY p.postedAt DESC",
            Post::class.java
        )
            .setParameter("authorId", authorId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }
}
