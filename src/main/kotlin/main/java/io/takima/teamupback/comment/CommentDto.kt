package main.java.io.takima.teamupback.comment

// Request DTOs
data class CommentCreateRequest(
    val content: String,
    val authorId: Int,
    val postId: Int
)

data class CommentUpdateRequest(
    val content: String? = null
)

// Response DTOs
data class CommentResponse(
    val id: Int?,
    val content: String,
    val authorId: Int,
    val postId: Int,
    val createdAt: String?
) {
    companion object {
        fun fromEntity(entity: Comment): CommentResponse {
            return CommentResponse(
                id = entity.id,
                content = entity.content,
                authorId = entity.authorId,
                postId = entity.post.id ?: 0,
                createdAt = entity.createdAt?.toString()
            )
        }
    }
}

data class CommentListResponse(
    val data: List<CommentResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
