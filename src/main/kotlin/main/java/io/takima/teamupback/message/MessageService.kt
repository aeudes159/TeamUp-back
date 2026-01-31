package main.java.io.takima.teamupback.message

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import main.java.io.takima.teamupback.discussion.DiscussionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MessageService(
    private val messageRepository: MessageRepository,
    private val messageDao: MessageDao,
    private val discussionRepository: DiscussionRepository
) {

    fun findAll(page: Int, size: Int): MessageListResponse {
        val offset = page * size
        val messages = messageDao.findAllWithPagination(offset, size)
        val total = messageDao.count()

        return MessageListResponse(
            data = messages.map { MessageResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): MessageResponse {
        val message = messageRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Message", "id", id) }
        return MessageResponse.fromEntity(message)
    }

    fun create(request: MessageCreateRequest): MessageResponse {
        val discussion = request.discussionId?.let {
            discussionRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Discussion", "id", it) }
        }

        val message = Message(
            content = request.content,
            imageUrl = request.imageUrl,
            senderId = request.senderId,
            discussion = discussion
        )
        val saved = messageRepository.save(message)
        return MessageResponse.fromEntity(saved)
    }

    fun update(id: Int, request: MessageUpdateRequest): MessageResponse {
        val message = messageRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Message", "id", id) }

        request.content?.let { message.content = it }
        request.imageUrl?.let { message.imageUrl = it }

        val saved = messageRepository.save(message)
        return MessageResponse.fromEntity(saved)
    }

    fun delete(id: Int) {
        if (!messageRepository.existsById(id)) {
            throw ResourceNotFoundException("Message", "id", id)
        }
        messageRepository.deleteById(id)
    }

    fun findByDiscussionId(discussionId: Int, page: Int, size: Int): MessageListResponse {
        val offset = page * size
        val messages = messageDao.findByDiscussionIdWithPagination(discussionId, offset, size)
        val total = messageRepository.findByDiscussionId(discussionId).size.toLong()

        return MessageListResponse(
            data = messages.map { MessageResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }
}
