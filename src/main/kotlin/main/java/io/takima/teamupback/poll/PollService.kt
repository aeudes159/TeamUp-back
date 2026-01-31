package main.java.io.takima.teamupback.poll

import main.java.io.takima.teamupback.common.exception.BadRequestException
import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import main.java.io.takima.teamupback.discussion.DiscussionRepository
import main.java.io.takima.teamupback.groupMember.GroupMemberId
import main.java.io.takima.teamupback.groupMember.GroupMemberRepository
import main.java.io.takima.teamupback.location.LocationRepository
import main.java.io.takima.teamupback.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PollService(
    private val pollRepository: PollRepository,
    private val pollOptionRepository: PollOptionRepository,
    private val pollVoteRepository: PollVoteRepository,
    private val pollDao: PollDao,
    private val discussionRepository: DiscussionRepository,
    private val groupMemberRepository: GroupMemberRepository,
    private val userRepository: UserRepository,
    private val locationRepository: LocationRepository
) {

    // ============================================
    // Group Membership Verification
    // ============================================

    private fun verifyGroupMembership(discussionId: Int, userId: Int) {
        val discussion = discussionRepository.findById(discussionId)
            .orElseThrow { ResourceNotFoundException("Discussion", "id", discussionId) }
        val groupId = discussion.group?.id
            ?: throw BadRequestException("Discussion not associated with group")
        val memberId = GroupMemberId(groupId, userId)
        if (!groupMemberRepository.existsById(memberId)) {
            throw BadRequestException("User is not a member of this group")
        }
    }

    private fun verifyGroupMembershipByPoll(pollId: Int, userId: Int) {
        val poll = pollRepository.findById(pollId)
            .orElseThrow { ResourceNotFoundException("Poll", "id", pollId) }
        val discussionId = poll.discussion?.id
            ?: throw BadRequestException("Poll not associated with discussion")
        verifyGroupMembership(discussionId, userId)
    }

    private fun verifyGroupMembershipByPollOption(pollOptionId: Int, userId: Int) {
        val pollOption = pollOptionRepository.findById(pollOptionId)
            .orElseThrow { ResourceNotFoundException("PollOption", "id", pollOptionId) }
        val pollId = pollOption.poll?.id
            ?: throw BadRequestException("PollOption not associated with poll")
        verifyGroupMembershipByPoll(pollId, userId)
    }

    // ============================================
    // Poll CRUD Operations
    // ============================================

    fun findByDiscussionId(discussionId: Int, page: Int, size: Int): PollListResponse {
        val offset = page * size
        val polls = pollDao.findByDiscussionIdWithPagination(discussionId, offset, size)
        val total = pollDao.countByDiscussionId(discussionId)

        val pollResponses = polls.map { poll ->
            val options = buildPollOptionsWithVotes(poll.id!!)
            PollResponse.fromEntity(poll, options)
        }

        return PollListResponse(
            data = pollResponses,
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): PollResponse {
        val poll = pollRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Poll", "id", id) }
        val options = buildPollOptionsWithVotes(id)
        return PollResponse.fromEntity(poll, options)
    }

    fun create(request: PollCreateRequest): PollResponse {
        verifyGroupMembership(request.discussionId, request.creatorId)

        val discussion = discussionRepository.findById(request.discussionId)
            .orElseThrow { ResourceNotFoundException("Discussion", "id", request.discussionId) }
        val creator = userRepository.findById(request.creatorId)
            .orElseThrow { ResourceNotFoundException("User", "id", request.creatorId) }

        val poll = Poll(
            title = request.title,
            description = request.description,
            discussion = discussion,
            creator = creator
        )

        val saved = pollRepository.save(poll)
        return PollResponse.fromEntity(saved)
    }

    fun update(id: Int, request: PollUpdateRequest, userId: Int): PollResponse {
        val poll = pollRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Poll", "id", id) }

        val discussionId = poll.discussion?.id
            ?: throw BadRequestException("Poll not associated with discussion")
        verifyGroupMembership(discussionId, userId)

        request.title?.let { poll.title = it }
        request.description?.let { poll.description = it }
        request.isActive?.let { poll.isActive = it }
        request.closedAt?.let { poll.closedAt = it }

        val saved = pollRepository.save(poll)
        val options = buildPollOptionsWithVotes(id)
        return PollResponse.fromEntity(saved, options)
    }

    fun closePoll(id: Int, userId: Int): PollResponse {
        val poll = pollRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Poll", "id", id) }

        val discussionId = poll.discussion?.id
            ?: throw BadRequestException("Poll not associated with discussion")
        verifyGroupMembership(discussionId, userId)

        if (poll.closedAt != null) {
            throw BadRequestException("Poll is already closed")
        }

        poll.closedAt = java.time.LocalDateTime.now()
        poll.isActive = false

        val saved = pollRepository.save(poll)
        val options = buildPollOptionsWithVotes(id)
        return PollResponse.fromEntity(saved, options)
    }

    fun delete(id: Int, userId: Int) {
        val poll = pollRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Poll", "id", id) }

        val discussionId = poll.discussion?.id
            ?: throw BadRequestException("Poll not associated with discussion")
        verifyGroupMembership(discussionId, userId)

        pollRepository.deleteById(id)
    }

    // ============================================
    // Poll Option Operations
    // ============================================

    fun addOption(request: PollOptionCreateRequest): PollOptionResponse {
        verifyGroupMembershipByPoll(request.pollId, request.addedByUserId)

        if (pollOptionRepository.existsByPollIdAndLocationId(request.pollId, request.locationId)) {
            throw BadRequestException("Location already added to this poll")
        }

        val poll = pollRepository.findById(request.pollId)
            .orElseThrow { ResourceNotFoundException("Poll", "id", request.pollId) }
        val location = locationRepository.findById(request.locationId)
            .orElseThrow { ResourceNotFoundException("Location", "id", request.locationId) }
        val addedByUser = userRepository.findById(request.addedByUserId)
            .orElseThrow { ResourceNotFoundException("User", "id", request.addedByUserId) }

        val pollOption = PollOption(
            poll = poll,
            location = location,
            addedByUser = addedByUser
        )

        val saved = pollOptionRepository.save(pollOption)
        return PollOptionResponse.fromEntity(saved)
    }

    fun removeOption(id: Int, userId: Int) {
        verifyGroupMembershipByPollOption(id, userId)

        if (!pollOptionRepository.existsById(id)) {
            throw ResourceNotFoundException("PollOption", "id", id)
        }
        pollOptionRepository.deleteById(id)
    }

    fun findOptionsByPollId(pollId: Int): List<PollOptionResponse> {
        return buildPollOptionsWithVotes(pollId)
    }

    // ============================================
    // Poll Vote Operations
    // ============================================

    fun vote(request: PollVoteCreateRequest): PollVoteResponse {
        verifyGroupMembershipByPollOption(request.pollOptionId, request.userId)

        val pollOption = pollOptionRepository.findById(request.pollOptionId)
            .orElseThrow { ResourceNotFoundException("PollOption", "id", request.pollOptionId) }

        // Check if poll is closed
        val poll = pollOption.poll
            ?: throw BadRequestException("PollOption not associated with a poll")
        if (poll.closedAt != null) {
            throw BadRequestException("Cannot vote on a closed poll")
        }

        if (pollVoteRepository.existsByPollOptionIdAndUserId(request.pollOptionId, request.userId)) {
            throw BadRequestException("User has already voted for this option")
        }

        val user = userRepository.findById(request.userId)
            .orElseThrow { ResourceNotFoundException("User", "id", request.userId) }

        val vote = PollVote(
            pollOption = pollOption,
            user = user
        )

        val saved = pollVoteRepository.save(vote)
        return PollVoteResponse.fromEntity(saved)
    }

    fun removeVote(optionId: Int, userId: Int) {
        verifyGroupMembershipByPollOption(optionId, userId)

        val vote = pollVoteRepository.findByPollOptionIdAndUserId(optionId, userId)
            ?: throw ResourceNotFoundException("PollVote", "optionId-userId", "$optionId-$userId")

        pollVoteRepository.delete(vote)
    }

    fun findVotesByOptionId(optionId: Int): List<PollVoteResponse> {
        val votes = pollVoteRepository.findByPollOptionId(optionId)
        return votes.map { PollVoteResponse.fromEntity(it) }
    }

    // ============================================
    // Helper Methods
    // ============================================

    private fun buildPollOptionsWithVotes(pollId: Int): List<PollOptionResponse> {
        val options = pollOptionRepository.findByPollId(pollId)
        return options.map { option ->
            val votes = pollVoteRepository.findByPollOptionId(option.id!!)
            val voteResponses = votes.map { PollVoteResponse.fromEntity(it) }
            PollOptionResponse.fromEntity(option, voteResponses)
        }
    }
}
