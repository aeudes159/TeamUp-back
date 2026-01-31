package main.java.io.takima.teamupback.groupmember

import java.time.LocalDateTime

// Request DTOs
data class GroupMemberCreateRequest(
    val groupId: Int,
    val userId: Int
)

// Response DTOs
data class GroupMemberResponse(
    val groupId: Int,
    val userId: Int,
    val joinedAt: LocalDateTime?
) {
    companion object {
        fun fromEntity(entity: GroupMember): GroupMemberResponse {
            return GroupMemberResponse(
                groupId = entity.id.groupId,
                userId = entity.id.userId,
                joinedAt = entity.joinedAt
            )
        }
    }
}

data class GroupMemberListResponse(
    val data: List<GroupMemberResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
