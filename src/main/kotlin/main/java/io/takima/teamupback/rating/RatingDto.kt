package main.java.io.takima.teamupback.rating

import java.time.LocalDateTime

// Request DTOs
data class RatingCreateRequest(
    val ratingValue: Int? = null,
    val userId: Int? = null,
    val locationId: Int? = null
)

data class RatingUpdateRequest(
    val ratingValue: Int? = null
)

// Response DTOs
data class RatingResponse(
    val id: Int?,
    val ratingValue: Int?,
    val userId: Int?,
    val locationId: Int?,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun fromEntity(entity: Rating): RatingResponse {
            return RatingResponse(
                id = entity.id,
                ratingValue = entity.ratingValue,
                userId = entity.userId,
                locationId = entity.location?.id,
                createdAt = entity.createdAt
            )
        }
    }
}

data class RatingListResponse(
    val data: List<RatingResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
