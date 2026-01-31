package main.java.io.takima.teamupback.activityfeed

// Request DTOs
data class ActivityFeedCreateRequest(
    val id: Int? = null
)

data class ActivityFeedUpdateRequest(
    val id: Int? = null
)

// Response DTOs
data class ActivityFeedResponse(
    val id: Int?
) {
    companion object {
        fun fromEntity(entity: ActivityFeed): ActivityFeedResponse {
            return ActivityFeedResponse(
                id = entity.id
            )
        }
    }
}

data class ActivityFeedListResponse(
    val data: List<ActivityFeedResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
