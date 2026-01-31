package main.java.io.takima.teamupback.post

import main.java.io.takima.teamupback.common.dao.BaseDao
import org.springframework.stereotype.Component

/**
 * PostDao - Extends BaseDao for common pagination methods.
 * Custom method for finding posts by author.
 */
@Component
class PostDao : BaseDao<Post>(Post::class) {

    override fun getDefaultOrderBy(): String = "e.postedAt DESC"

    /**
     * Find posts by author ID with pagination
     */
    fun findByAuthorIdWithPagination(authorId: Int, offset: Int, limit: Int): List<Post> {
        return findByFieldWithPagination(
            fieldName = "authorId",
            fieldValue = authorId,
            offset = offset,
            limit = limit
        )
    }

    /**
     * Count posts by an author
     */
    fun countByAuthorId(authorId: Int): Long {
        return countByField("authorId", authorId)
    }
}
