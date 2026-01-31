package main.java.io.takima.teamupback.poll

import java.time.LocalDateTime

// ============================================
// Poll Request DTOs
// ============================================

data class PollCreateRequest(
    val title: String,
    val description: String? = null,
    val discussionId: Int,
    val creatorId: Int
)

data class PollUpdateRequest(
    val title: String? = null,
    val description: String? = null,
    val isActive: Boolean? = null,
    val closedAt: LocalDateTime? = null
)

// ============================================
// Poll Option Request DTOs
// ============================================

data class PollOptionCreateRequest(
    val pollId: Int,
    val locationId: Int,
    val addedByUserId: Int
)

// ============================================
// Poll Vote Request DTOs
// ============================================

data class PollVoteCreateRequest(
    val pollOptionId: Int,
    val userId: Int
)

// ============================================
// Poll Response DTOs
// ============================================

data class PollResponse(
    val id: Int?,
    val title: String,
    val description: String?,
    val discussionId: Int?,
    val creatorId: Int?,
    val createdAt: LocalDateTime?,
    val isActive: Boolean,
    val closedAt: LocalDateTime?,
    val options: List<PollOptionResponse> = emptyList(),
    val totalVotes: Int = 0
) {
    companion object {
        fun fromEntity(entity: Poll, options: List<PollOptionResponse> = emptyList()): PollResponse {
            val totalVotes = options.sumOf { it.voteCount }
            return PollResponse(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                discussionId = entity.discussion?.id,
                creatorId = entity.creator?.id,
                createdAt = entity.createdAt,
                isActive = entity.isActive,
                closedAt = entity.closedAt,
                options = options,
                totalVotes = totalVotes
            )
        }
    }
}

data class PollOptionResponse(
    val id: Int?,
    val pollId: Int?,
    val locationId: Int?,
    val locationName: String?,
    val locationAddress: String?,
    val locationPictureUrl: String?,
    val addedByUserId: Int?,
    val createdAt: LocalDateTime?,
    val voteCount: Int = 0,
    val voters: List<PollVoteResponse> = emptyList()
) {
    companion object {
        fun fromEntity(entity: PollOption, votes: List<PollVoteResponse> = emptyList()): PollOptionResponse {
            return PollOptionResponse(
                id = entity.id,
                pollId = entity.poll?.id,
                locationId = entity.location?.id,
                locationName = entity.location?.name,
                locationAddress = entity.location?.address,
                locationPictureUrl = entity.location?.pictureUrl,
                addedByUserId = entity.addedByUser?.id,
                createdAt = entity.createdAt,
                voteCount = votes.size,
                voters = votes
            )
        }
    }
}

data class PollVoteResponse(
    val id: Int?,
    val pollOptionId: Int?,
    val userId: Int?,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun fromEntity(entity: PollVote): PollVoteResponse {
            return PollVoteResponse(
                id = entity.id,
                pollOptionId = entity.pollOption?.id,
                userId = entity.user?.id,
                createdAt = entity.createdAt
            )
        }
    }
}

// ============================================
// Poll List Response DTOs
// ============================================

data class PollListResponse(
    val data: List<PollResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)

data class PollOptionListResponse(
    val data: List<PollOptionResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)

data class PollVoteListResponse(
    val data: List<PollVoteResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
