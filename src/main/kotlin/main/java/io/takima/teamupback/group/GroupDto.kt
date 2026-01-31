package main.java.io.takima.teamupback.group

import java.time.LocalDateTime

// Request DTOs
data class GroupCreateRequest(
    val name: String,
    val coverPictureUrl: String? = null,
    val isPublic: Boolean
)

data class GroupUpdateRequest(
    val name: String? = null,
    val coverPictureUrl: String? = null,
    val isPublic: Boolean? = null
)

// Response DTOs
data class GroupResponse(
    val id: Int?,
    val name: String,
    val coverPictureUrl: String?,
    val createdAt: LocalDateTime?,
    val isPublic: Boolean
) {
    companion object {
        fun fromEntity(entity: Group): GroupResponse {
            return GroupResponse(
                id = entity.id,
                name = entity.name,
                coverPictureUrl = entity.coverPictureUrl,
                createdAt = entity.createdAt,
                isPublic = entity.isPublic
            )
        }
    }
}

data class GroupListResponse(
    val data: List<GroupResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
