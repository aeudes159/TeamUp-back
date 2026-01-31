package main.java.io.takima.teamupback.activityFeed

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ActivityFeedService(
    private val activityFeedRepository: ActivityFeedRepository,
    private val activityFeedDao: ActivityFeedDao
) {

    fun findAll(page: Int, size: Int): ActivityFeedListResponse {
        val offset = page * size
        val activityFeeds = activityFeedDao.findAllWithPagination(offset, size)
        val total = activityFeedDao.count()

        return ActivityFeedListResponse(
            data = activityFeeds.map { ActivityFeedResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): ActivityFeedResponse {
        val activityFeed = activityFeedRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("ActivityFeed", "id", id) }
        return ActivityFeedResponse.fromEntity(activityFeed)
    }

    fun create(request: ActivityFeedCreateRequest): ActivityFeedResponse {
        val activityFeed = ActivityFeed()
        val saved = activityFeedRepository.save(activityFeed)
        return ActivityFeedResponse.fromEntity(saved)
    }

    fun update(id: Int, request: ActivityFeedUpdateRequest): ActivityFeedResponse {
        val activityFeed = activityFeedRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("ActivityFeed", "id", id) }
        val saved = activityFeedRepository.save(activityFeed)
        return ActivityFeedResponse.fromEntity(saved)
    }

    fun delete(id: Int) {
        if (!activityFeedRepository.existsById(id)) {
            throw ResourceNotFoundException("ActivityFeed", "id", id)
        }
        activityFeedRepository.deleteById(id)
    }
}
