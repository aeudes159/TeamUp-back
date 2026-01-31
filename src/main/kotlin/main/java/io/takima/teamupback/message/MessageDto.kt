package main.java.io.takima.teamupback.message

import java.time.LocalDateTime

// Request DTOs
data class MessageCreateRequest(
    val content: String? = null,
    val imageUrl: String? = null,
    val senderId: Int? = null,
    val discussionId: Int? = null
)

data class MessageUpdateRequest(
    val content: String? = null,
    val imageUrl: String? = null
)

// Response DTOs
data class MessageResponse(
    val id: Int?,
    val content: String?,
    val imageUrl: String?,
    val sentAt: LocalDateTime?,
    val senderId: Int?,
    val discussionId: Int?
) {
    companion object {
        fun fromEntity(entity: Message): MessageResponse {
            return MessageResponse(
                id = entity.id,
                content = entity.content,
                imageUrl = entity.imageUrl,
                sentAt = entity.sentAt,
                senderId = entity.senderId,
                discussionId = entity.discussion?.id
            )
        }
    }
}

data class MessageListResponse(
    val data: List<MessageResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
