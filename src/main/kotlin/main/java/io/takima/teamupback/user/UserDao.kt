package main.java.io.takima.teamupback.user

import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * UserDao - Extends BaseDao for common pagination methods.
 * Custom searchByName method for user-specific search.
 */
@Component
class UserDao : BaseDao<User>(User::class) {

    override fun getDefaultOrderBy(): String = "e.lastName, e.firstName"

    /**
     * Search users by first name or last name (case-insensitive)
     */
    fun searchByName(name: String, offset: Int, limit: Int): List<User> {
        return findWithPagination(
            whereClause = "LOWER(e.firstName) LIKE LOWER(:name) OR LOWER(e.lastName) LIKE LOWER(:name)",
            parameters = mapOf("name" to "%$name%"),
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count users matching a search term
     */
    fun countByName(name: String): Long {
        return countWhere(
            "LOWER(e.firstName) LIKE LOWER(:name) OR LOWER(e.lastName) LIKE LOWER(:name)",
            mapOf("name" to "%$name%")
        )
    }
}
