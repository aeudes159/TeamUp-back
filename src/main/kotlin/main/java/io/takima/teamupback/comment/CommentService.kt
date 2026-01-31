package main.java.io.takima.teamupback.comment

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import main.java.io.takima.teamupback.post.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val commentDao: CommentDao,
    private val postRepository: PostRepository
) {

    fun findAll(page: Int, size: Int): CommentListResponse {
        val offset = page * size
        val comments = commentDao.findAllWithPagination(offset, size)
        val total = commentDao.count()

        return CommentListResponse(
            data = comments.map { CommentResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): CommentResponse {
        val comment = commentRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Comment", "id", id) }
        return CommentResponse.fromEntity(comment)
    }

    fun findByPostId(postId: Int, page: Int, size: Int): CommentListResponse {
        val offset = page * size
        val comments = commentDao.findByPostIdWithPagination(postId, offset, size)
        val total = commentDao.countByPostId(postId)

        return CommentListResponse(
            data = comments.map { CommentResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun create(request: CommentCreateRequest): CommentResponse {
        val post = postRepository.findById(request.postId)
            .orElseThrow { ResourceNotFoundException("Post", "id", request.postId) }

        val comment = Comment(
            content = request.content,
            authorId = request.authorId,
            post = post
        )
        val saved = commentRepository.save(comment)
        return CommentResponse.fromEntity(saved)
    }

    fun update(id: Int, request: CommentUpdateRequest): CommentResponse {
        val comment = commentRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Comment", "id", id) }

        request.content?.let { comment.content = it }

        val saved = commentRepository.save(comment)
        return CommentResponse.fromEntity(saved)
    }

    fun delete(id: Int) {
        if (!commentRepository.existsById(id)) {
            throw ResourceNotFoundException("Comment", "id", id)
        }
        commentRepository.deleteById(id)
    }

    fun countByPostId(postId: Int): Long {
        return commentDao.countByPostId(postId)
    }
}
