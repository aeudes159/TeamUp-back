package main.java.io.takima.teamupback.message

import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * MessageDao - Extends BaseDao for common pagination methods.
 * Custom method for finding messages by discussion.
 */
@Component
class MessageDao : BaseDao<Message>(Message::class) {

    override fun getDefaultOrderBy(): String = "e.sentAt DESC"

    /**
     * Find messages by discussion ID with pagination
     */
    fun findByDiscussionIdWithPagination(discussionId: Int, offset: Int, limit: Int): List<Message> {
        return findWithPagination(
            whereClause = "e.discussion.id = :discussionId",
            parameters = mapOf("discussionId" to discussionId),
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count messages in a discussion
     */
    fun countByDiscussionId(discussionId: Int): Long {
        return countWhere("e.discussion.id = :discussionId", mapOf("discussionId" to discussionId))
    }
}
