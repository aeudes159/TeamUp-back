package main.java.io.takima.teamupback.proposal

import java.time.LocalDateTime

// Request DTOs
data class ProposalCreateRequest(
    val discussionId: Int? = null
)

data class ProposalUpdateRequest(
    val discussionId: Int? = null
)

// Response DTOs
data class ProposalResponse(
    val id: Int?,
    val discussionId: Int?,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun fromEntity(entity: Proposal): ProposalResponse {
            return ProposalResponse(
                id = entity.id,
                discussionId = entity.discussion?.id,
                createdAt = entity.createdAt
            )
        }
    }
}

data class ProposalListResponse(
    val data: List<ProposalResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
