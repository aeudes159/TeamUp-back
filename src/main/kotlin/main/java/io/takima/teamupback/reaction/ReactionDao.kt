package main.java.io.takima.teamupback.reaction

import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * ReactionDao - Extends BaseDao for common pagination methods.
 * Custom methods for finding reactions by message or user.
 */
@Component
class ReactionDao : BaseDao<Reaction>(Reaction::class) {

    override fun getDefaultOrderBy(): String = "e.createdAt DESC"

    /**
     * Find reactions by message ID with pagination
     */
    fun findByMessageIdWithPagination(messageId: Int, offset: Int, limit: Int): List<Reaction> {
        return findByFieldWithPagination(
            fieldName = "messageId",
            fieldValue = messageId,
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count reactions on a message
     */
    fun countByMessageId(messageId: Int): Long {
        return countByField("messageId", messageId)
    }

    /**
     * Find reactions by user ID with pagination
     */
    fun findByUserIdWithPagination(userId: Int, offset: Int, limit: Int): List<Reaction> {
        return findByFieldWithPagination(
            fieldName = "userId",
            fieldValue = userId,
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count reactions by a user
     */
    fun countByUserId(userId: Int): Long {
        return countByField("userId", userId)
    }
}
