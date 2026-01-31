package main.java.io.takima.teamupback.poll

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class PollDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findByDiscussionIdWithPagination(discussionId: Int, offset: Int, limit: Int): List<Poll> {
        return entityManager.createQuery(
            "SELECT p FROM Poll p WHERE p.discussion.id = :discussionId ORDER BY p.createdAt DESC",
            Poll::class.java
        )
            .setParameter("discussionId", discussionId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun countByDiscussionId(discussionId: Int): Long {
        return entityManager.createQuery(
            "SELECT COUNT(p) FROM Poll p WHERE p.discussion.id = :discussionId",
            Long::class.javaObjectType
        )
            .setParameter("discussionId", discussionId)
            .singleResult
    }

    fun findAllWithPagination(offset: Int, limit: Int): List<Poll> {
        return entityManager.createQuery(
            "SELECT p FROM Poll p ORDER BY p.createdAt DESC",
            Poll::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(p) FROM Poll p",
            Long::class.javaObjectType
        )
            .singleResult
    }
}

@Component
class PollOptionDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findByPollIdWithPagination(pollId: Int, offset: Int, limit: Int): List<PollOption> {
        return entityManager.createQuery(
            "SELECT po FROM PollOption po WHERE po.poll.id = :pollId ORDER BY po.createdAt ASC",
            PollOption::class.java
        )
            .setParameter("pollId", pollId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun countByPollId(pollId: Int): Long {
        return entityManager.createQuery(
            "SELECT COUNT(po) FROM PollOption po WHERE po.poll.id = :pollId",
            Long::class.javaObjectType
        )
            .setParameter("pollId", pollId)
            .singleResult
    }
}

@Component
class PollVoteDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findByPollOptionIdWithPagination(pollOptionId: Int, offset: Int, limit: Int): List<PollVote> {
        return entityManager.createQuery(
            "SELECT pv FROM PollVote pv WHERE pv.pollOption.id = :pollOptionId ORDER BY pv.createdAt ASC",
            PollVote::class.java
        )
            .setParameter("pollOptionId", pollOptionId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun countByPollOptionId(pollOptionId: Int): Long {
        return entityManager.createQuery(
            "SELECT COUNT(pv) FROM PollVote pv WHERE pv.pollOption.id = :pollOptionId",
            Long::class.javaObjectType
        )
            .setParameter("pollOptionId", pollOptionId)
            .singleResult
    }
}
