package main.java.io.takima.teamupback.proposal

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import main.java.io.takima.teamupback.discussion.DiscussionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProposalService(
    private val proposalRepository: ProposalRepository,
    private val proposalDao: ProposalDao,
    private val discussionRepository: DiscussionRepository
) {

    fun findAll(page: Int, size: Int): ProposalListResponse {
        val offset = page * size
        val proposals = proposalDao.findAllWithPagination(offset, size)
        val total = proposalDao.count()

        return ProposalListResponse(
            data = proposals.map { ProposalResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): ProposalResponse {
        val proposal = proposalRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Proposal", "id", id) }
        return ProposalResponse.fromEntity(proposal)
    }

    fun create(request: ProposalCreateRequest): ProposalResponse {
        val discussion = request.discussionId?.let {
            discussionRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Discussion", "id", it) }
        }

        val proposal = Proposal(
            discussion = discussion
        )
        val saved = proposalRepository.save(proposal)
        return ProposalResponse.fromEntity(saved)
    }

    fun update(id: Int, request: ProposalUpdateRequest): ProposalResponse {
        val proposal = proposalRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Proposal", "id", id) }

        request.discussionId?.let { discussionId ->
            proposal.discussion = discussionRepository.findById(discussionId)
                .orElseThrow { ResourceNotFoundException("Discussion", "id", discussionId) }
        }

        val saved = proposalRepository.save(proposal)
        return ProposalResponse.fromEntity(saved)
    }

    fun delete(id: Int) {
        if (!proposalRepository.existsById(id)) {
            throw ResourceNotFoundException("Proposal", "id", id)
        }
        proposalRepository.deleteById(id)
    }

    fun findByDiscussionId(discussionId: Int, page: Int, size: Int): ProposalListResponse {
        val offset = page * size
        val proposals = proposalDao.findByDiscussionIdWithPagination(discussionId, offset, size)
        val total = proposalRepository.findByDiscussionId(discussionId).size.toLong()

        return ProposalListResponse(
            data = proposals.map { ProposalResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }
}
