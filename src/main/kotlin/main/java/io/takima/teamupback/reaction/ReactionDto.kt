package main.java.io.takima.teamupback.reaction

import java.time.LocalDateTime

// Request DTOs
data class ReactionCreateRequest(
    val emoji: String,
    val userId: Int,
    val messageId: Int
)

// Response DTOs
data class ReactionResponse(
    val id: Int?,
    val emoji: String,
    val userId: Int,
    val messageId: Int,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun fromEntity(entity: Reaction): ReactionResponse {
            return ReactionResponse(
                id = entity.id,
                emoji = entity.emoji,
                userId = entity.userId,
                messageId = entity.messageId,
                createdAt = entity.createdAt
            )
        }
    }
}

data class ReactionListResponse(
    val data: List<ReactionResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
