package main.java.io.takima.teamupback.group

import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * GroupDao - Extends BaseDao for common pagination methods.
 * Custom method for finding public groups.
 */
@Component
class GroupDao : BaseDao<Group>(Group::class) {

    override fun getDefaultOrderBy(): String = "e.createdAt DESC"

    /**
     * Find all public groups with pagination
     */
    fun findPublicGroups(offset: Int, limit: Int): List<Group> {
        return findByFieldWithPagination(
            fieldName = "isPublic",
            fieldValue = true,
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count public groups
     */
    fun countPublicGroups(): Long {
        return countByField("isPublic", true)
    }
}
