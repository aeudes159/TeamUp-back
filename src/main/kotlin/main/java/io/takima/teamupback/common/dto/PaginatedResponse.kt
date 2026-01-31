package main.java.io.takima.teamupback.common.dto

/**
 * Generic paginated response wrapper for list endpoints.
 * Replaces individual *ListResponse DTOs (UserListResponse, GroupListResponse, etc.)
 * 
 * Usage:
 * ```kotlin
 * fun findAll(page: Int, size: Int): PaginatedResponse<UserResponse> {
 *     val users = userDao.findAllWithPagination(offset, size)
 *     return PaginatedResponse(
 *         data = users.map { UserResponse.fromEntity(it) },
 *         total = userDao.count(),
 *         page = page,
 *         size = size
 *     )
 * }
 * ```
 */
data class PaginatedResponse<T>(
    val data: List<T>,
    val total: Long,
    val page: Int,
    val size: Int
) {
    companion object {
        /**
         * Create an empty paginated response
         */
        fun <T> empty(page: Int = 0, size: Int = 20): PaginatedResponse<T> {
            return PaginatedResponse(
                data = emptyList(),
                total = 0,
                page = page,
                size = size
            )
        }

        /**
         * Create a paginated response from a list of items with computed total
         */
        fun <T> of(
            data: List<T>,
            total: Long,
            page: Int,
            size: Int
        ): PaginatedResponse<T> {
            return PaginatedResponse(data, total, page, size)
        }
    }
}
