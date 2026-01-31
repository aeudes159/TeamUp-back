package main.java.io.takima.teamupback.discussion

import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * DiscussionDao - Extends BaseDao for common pagination methods.
 * Custom method for finding discussions by group.
 */
@Component
class DiscussionDao : BaseDao<Discussion>(Discussion::class) {

    override fun getDefaultOrderBy(): String = "e.createdAt DESC"

    /**
     * Find discussions by group ID with pagination
     */
    fun findByGroupIdWithPagination(groupId: Int, offset: Int, limit: Int): List<Discussion> {
        return findWithPagination(
            whereClause = "e.group.id = :groupId",
            parameters = mapOf("groupId" to groupId),
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count discussions in a group
     */
    fun countByGroupId(groupId: Int): Long {
        return countWhere("e.group.id = :groupId", mapOf("groupId" to groupId))
    }
}
