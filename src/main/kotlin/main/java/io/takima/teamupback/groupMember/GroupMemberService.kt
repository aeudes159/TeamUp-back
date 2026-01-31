package main.java.io.takima.teamupback.groupMember

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import main.java.io.takima.teamupback.group.GroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GroupMemberService(
    private val groupMemberRepository: GroupMemberRepository,
    private val groupMemberDao: GroupMemberDao,
    private val groupRepository: GroupRepository
) {

    fun findAll(page: Int, size: Int): GroupMemberListResponse {
        val offset = page * size
        val groupMembers = groupMemberDao.findAllWithPagination(offset, size)
        val total = groupMemberDao.count()

        return GroupMemberListResponse(
            data = groupMembers.map { GroupMemberResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(groupId: Int, userId: Int): GroupMemberResponse {
        val id = GroupMemberId(groupId, userId)
        val groupMember = groupMemberRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("GroupMember", "id", "$groupId-$userId") }
        return GroupMemberResponse.fromEntity(groupMember)
    }

    fun create(request: GroupMemberCreateRequest): GroupMemberResponse {
        val group = groupRepository.findById(request.groupId)
            .orElseThrow { ResourceNotFoundException("Group", "id", request.groupId) }

        val id = GroupMemberId(request.groupId, request.userId)
        val groupMember = GroupMember(
            id = id,
            group = group
        )
        val saved = groupMemberRepository.save(groupMember)
        return GroupMemberResponse.fromEntity(saved)
    }

    fun delete(groupId: Int, userId: Int) {
        val id = GroupMemberId(groupId, userId)
        if (!groupMemberRepository.existsById(id)) {
            throw ResourceNotFoundException("GroupMember", "id", "$groupId-$userId")
        }
        groupMemberRepository.deleteById(id)
    }

    fun findByGroupId(groupId: Int, page: Int, size: Int): GroupMemberListResponse {
        val offset = page * size
        val groupMembers = groupMemberDao.findByGroupId(groupId, offset, size)
        val total = groupMemberRepository.findByIdGroupId(groupId).size.toLong()

        return GroupMemberListResponse(
            data = groupMembers.map { GroupMemberResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findByUserId(userId: Int, page: Int, size: Int): GroupMemberListResponse {
        val offset = page * size
        val groupMembers = groupMemberDao.findByUserId(userId, offset, size)
        val total = groupMemberRepository.findByIdUserId(userId).size.toLong()

        return GroupMemberListResponse(
            data = groupMembers.map { GroupMemberResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }
}
