package main.java.io.takima.teamupback.discussion

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import main.java.io.takima.teamupback.group.GroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DiscussionService(
    private val discussionRepository: DiscussionRepository,
    private val discussionDao: DiscussionDao,
    private val groupRepository: GroupRepository
) {

    fun findAll(page: Int, size: Int): DiscussionListResponse {
        val offset = page * size
        val discussions = discussionDao.findAllWithPagination(offset, size)
        val total = discussionDao.count()

        return DiscussionListResponse(
            data = discussions.map { DiscussionResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): DiscussionResponse {
        val discussion = discussionRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Discussion", "id", id) }
        return DiscussionResponse.fromEntity(discussion)
    }

    fun create(request: DiscussionCreateRequest): DiscussionResponse {
        val group = request.groupId?.let {
            groupRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Group", "id", it) }
        }

        val discussion = Discussion(
            group = group,
            backgroundImageUrl = request.backgroundImageUrl
        )
        val saved = discussionRepository.save(discussion)
        return DiscussionResponse.fromEntity(saved)
    }

    fun update(id: Int, request: DiscussionUpdateRequest): DiscussionResponse {
        val discussion = discussionRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Discussion", "id", id) }

        request.groupId?.let { groupId ->
            discussion.group = groupRepository.findById(groupId)
                .orElseThrow { ResourceNotFoundException("Group", "id", groupId) }
        }
        request.backgroundImageUrl?.let { discussion.backgroundImageUrl = it }

        val saved = discussionRepository.save(discussion)
        return DiscussionResponse.fromEntity(saved)
    }

    fun delete(id: Int) {
        if (!discussionRepository.existsById(id)) {
            throw ResourceNotFoundException("Discussion", "id", id)
        }
        discussionRepository.deleteById(id)
    }

    fun findByGroupId(groupId: Int, page: Int, size: Int): DiscussionListResponse {
        val offset = page * size
        val discussions = discussionDao.findByGroupIdWithPagination(groupId, offset, size)
        val total = discussionRepository.findByGroupId(groupId).size.toLong()

        return DiscussionListResponse(
            data = discussions.map { DiscussionResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }
}
