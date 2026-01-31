package main.java.io.takima.teamupback.proposal

import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * ProposalDao - Extends BaseDao for common pagination methods.
 * Custom method for finding proposals by discussion.
 */
@Component
class ProposalDao : BaseDao<Proposal>(Proposal::class) {

    override fun getDefaultOrderBy(): String = "e.createdAt DESC"

    /**
     * Find proposals by discussion ID with pagination
     */
    fun findByDiscussionIdWithPagination(discussionId: Int, offset: Int, limit: Int): List<Proposal> {
        return findWithPagination(
            whereClause = "e.discussion.id = :discussionId",
            parameters = mapOf("discussionId" to discussionId),
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count proposals in a discussion
     */
    fun countByDiscussionId(discussionId: Int): Long {
        return countWhere("e.discussion.id = :discussionId", mapOf("discussionId" to discussionId))
    }
}
