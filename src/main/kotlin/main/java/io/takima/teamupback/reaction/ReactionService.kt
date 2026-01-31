package main.java.io.takima.teamupback.reaction

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReactionService(
    private val reactionRepository: ReactionRepository,
    private val reactionDao: ReactionDao
) {
    fun findAll(page: Int, size: Int): ReactionListResponse {
        val offset = page * size
        val reactions = reactionDao.findAllWithPagination(offset, size)
        val total = reactionDao.count()

        return ReactionListResponse(
            data = reactions.map { ReactionResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): ReactionResponse {
        val reaction = reactionRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Reaction", "id", id) }
        return ReactionResponse.fromEntity(reaction)
    }

    fun create(request: ReactionCreateRequest): ReactionResponse {
        // Check if user already has this reaction on this message
        val existing = reactionRepository.findByMessageIdAndUserIdAndEmoji(
            request.messageId,
            request.userId,
            request.emoji
        )
        if (existing != null) {
            // Return existing reaction instead of creating a duplicate
            return ReactionResponse.fromEntity(existing)
        }

        val reaction = Reaction(
            emoji = request.emoji,
            userId = request.userId,
            messageId = request.messageId
        )
        val savedReaction = reactionRepository.save(reaction)
        return ReactionResponse.fromEntity(savedReaction)
    }

    fun delete(id: Int) {
        if (!reactionRepository.existsById(id)) {
            throw ResourceNotFoundException("Reaction", "id", id)
        }
        reactionRepository.deleteById(id)
    }

    fun findByMessageId(messageId: Int, page: Int, size: Int): ReactionListResponse {
        val offset = page * size
        val reactions = reactionDao.findByMessageIdWithPagination(messageId, offset, size)
        val total = reactionDao.countByMessageId(messageId)

        return ReactionListResponse(
            data = reactions.map { ReactionResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findByUserId(userId: Int, page: Int, size: Int): ReactionListResponse {
        val offset = page * size
        val reactions = reactionDao.findByUserIdWithPagination(userId, offset, size)
        val total = reactionDao.countByUserId(userId)

        return ReactionListResponse(
            data = reactions.map { ReactionResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }
}
