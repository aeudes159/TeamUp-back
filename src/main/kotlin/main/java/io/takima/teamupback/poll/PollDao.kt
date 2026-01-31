package main.java.io.takima.teamupback.poll

import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * PollDao - Extends BaseDao for common pagination methods.
 * Custom method for finding polls by discussion.
 */
@Component
class PollDao : BaseDao<Poll>(Poll::class) {

    override fun getDefaultOrderBy(): String = "e.createdAt DESC"

    /**
     * Find polls by discussion ID with pagination
     */
    fun findByDiscussionIdWithPagination(discussionId: Int, offset: Int, limit: Int): List<Poll> {
        return findWithPagination(
            whereClause = "e.discussion.id = :discussionId",
            parameters = mapOf("discussionId" to discussionId),
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count polls in a discussion
     */
    fun countByDiscussionId(discussionId: Int): Long {
        return countWhere("e.discussion.id = :discussionId", mapOf("discussionId" to discussionId))
    }
}

/**
 * PollOptionDao - Extends BaseDao for common pagination methods.
 */
@Component
class PollOptionDao : BaseDao<PollOption>(PollOption::class) {

    override fun getDefaultOrderBy(): String = "e.createdAt ASC"

    /**
     * Find poll options by poll ID with pagination
     */
    fun findByPollIdWithPagination(pollId: Int, offset: Int, limit: Int): List<PollOption> {
        return findWithPagination(
            whereClause = "e.poll.id = :pollId",
            parameters = mapOf("pollId" to pollId),
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count poll options for a poll
     */
    fun countByPollId(pollId: Int): Long {
        return countWhere("e.poll.id = :pollId", mapOf("pollId" to pollId))
    }
}

/**
 * PollVoteDao - Extends BaseDao for common pagination methods.
 */
@Component
class PollVoteDao : BaseDao<PollVote>(PollVote::class) {

    override fun getDefaultOrderBy(): String = "e.createdAt ASC"

    /**
     * Find votes by poll option ID with pagination
     */
    fun findByPollOptionIdWithPagination(pollOptionId: Int, offset: Int, limit: Int): List<PollVote> {
        return findWithPagination(
            whereClause = "e.pollOption.id = :pollOptionId",
            parameters = mapOf("pollOptionId" to pollOptionId),
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count votes for a poll option
     */
    fun countByPollOptionId(pollOptionId: Int): Long {
        return countWhere("e.pollOption.id = :pollOptionId", mapOf("pollOptionId" to pollOptionId))
    }
}
