package main.java.io.takima.teamupback.group

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GroupService(
    private val groupRepository: GroupRepository,
    private val groupDao: GroupDao
) {

    fun findAll(page: Int, size: Int): GroupListResponse {
        val offset = page * size
        val groups = groupDao.findAllWithPagination(offset, size)
        val total = groupDao.count()

        return GroupListResponse(
            data = groups.map { GroupResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): GroupResponse {
        val group = groupRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Group", "id", id) }
        return GroupResponse.fromEntity(group)
    }

    fun create(request: GroupCreateRequest): GroupResponse {
        val group = Group(
            name = request.name,
            coverPictureUrl = request.coverPictureUrl,
            isPublic = request.isPublic
        )
        val saved = groupRepository.save(group)
        return GroupResponse.fromEntity(saved)
    }

    fun update(id: Int, request: GroupUpdateRequest): GroupResponse {
        val group = groupRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Group", "id", id) }

        request.name?.let { group.name = it }
        request.coverPictureUrl?.let { group.coverPictureUrl = it }
        request.isPublic?.let { group.isPublic = it }

        val saved = groupRepository.save(group)
        return GroupResponse.fromEntity(saved)
    }

    fun delete(id: Int) {
        if (!groupRepository.existsById(id)) {
            throw ResourceNotFoundException("Group", "id", id)
        }
        groupRepository.deleteById(id)
    }

    fun findPublicGroups(page: Int, size: Int): GroupListResponse {
        val offset = page * size
        val groups = groupDao.findPublicGroups(offset, size)
        val total = groupRepository.findByIsPublic(true).size.toLong()

        return GroupListResponse(
            data = groups.map { GroupResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }
}
