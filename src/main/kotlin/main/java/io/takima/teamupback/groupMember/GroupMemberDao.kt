package main.java.io.takima.teamupback.groupMember

import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * GroupMemberDao - Extends BaseDao for common pagination methods.
 * Custom methods for finding members by group or user.
 * Note: Uses composite key (groupId, userId) accessed via e.id.groupId / e.id.userId
 */
@Component
class GroupMemberDao : BaseDao<GroupMember>(GroupMember::class) {

    override fun getDefaultOrderBy(): String = "e.joinedAt DESC"

    /**
     * Find members by group ID with pagination
     */
    fun findByGroupId(groupId: Int, offset: Int, limit: Int): List<GroupMember> {
        return findWithPagination(
            whereClause = "e.id.groupId = :groupId",
            parameters = mapOf("groupId" to groupId),
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count members in a group
     */
    fun countByGroupId(groupId: Int): Long {
        return countWhere("e.id.groupId = :groupId", mapOf("groupId" to groupId))
    }

    /**
     * Find group memberships by user ID with pagination
     */
    fun findByUserId(userId: Int, offset: Int, limit: Int): List<GroupMember> {
        return findWithPagination(
            whereClause = "e.id.userId = :userId",
            parameters = mapOf("userId" to userId),
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count groups a user belongs to
     */
    fun countByUserId(userId: Int): Long {
        return countWhere("e.id.userId = :userId", mapOf("userId" to userId))
    }
}
