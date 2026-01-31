package main.java.io.takima.teamupback.proposal

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class ProposalDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<Proposal> {
        return entityManager.createQuery(
            "SELECT p FROM Proposal p ORDER BY p.createdAt DESC",
            Proposal::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(p) FROM Proposal p",
            Long::class.javaObjectType
        ).singleResult
    }

    fun findByDiscussionIdWithPagination(discussionId: Int, offset: Int, limit: Int): List<Proposal> {
        return entityManager.createQuery(
            "SELECT p FROM Proposal p WHERE p.discussion.id = :discussionId ORDER BY p.createdAt DESC",
            Proposal::class.java
        )
            .setParameter("discussionId", discussionId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }
}
