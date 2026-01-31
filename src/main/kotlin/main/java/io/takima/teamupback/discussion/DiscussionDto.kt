package main.java.io.takima.teamupback.discussion

import java.time.LocalDateTime

// Request DTOs
data class DiscussionCreateRequest(
    val groupId: Int? = null,
    val backgroundImageUrl: String? = null
)

data class DiscussionUpdateRequest(
    val groupId: Int? = null,
    val backgroundImageUrl: String? = null
)

// Response DTOs
data class DiscussionResponse(
    val id: Int?,
    val groupId: Int?,
    val backgroundImageUrl: String?,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun fromEntity(entity: Discussion): DiscussionResponse {
            return DiscussionResponse(
                id = entity.id,
                groupId = entity.group?.id,
                backgroundImageUrl = entity.backgroundImageUrl,
                createdAt = entity.createdAt
            )
        }
    }
}

data class DiscussionListResponse(
    val data: List<DiscussionResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
