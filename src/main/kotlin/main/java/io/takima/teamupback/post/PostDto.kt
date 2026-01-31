package main.java.io.takima.teamupback.post

import java.time.LocalDateTime

// Request DTOs
data class PostCreateRequest(
    val content: String? = null,
    val imageUrl: String? = null,
    val authorId: Int? = null,
    val locationId: Int? = null,
    val discussionId: Int? = null
)

data class PostUpdateRequest(
    val content: String? = null,
    val imageUrl: String? = null,
    val locationId: Int? = null
)

// Response DTOs
data class PostResponse(
    val id: Int?,
    val content: String?,
    val imageUrl: String?,
    val postedAt: LocalDateTime?,
    val authorId: Int?,
    val locationId: Int?,
    val discussionId: Int?
) {
    companion object {
        fun fromEntity(entity: Post): PostResponse {
            return PostResponse(
                id = entity.id,
                content = entity.content,
                imageUrl = entity.imageUrl,
                postedAt = entity.postedAt,
                authorId = entity.authorId,
                locationId = entity.location?.id,
                discussionId = entity.discussion?.id
            )
        }
    }
}

data class PostListResponse(
    val data: List<PostResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
