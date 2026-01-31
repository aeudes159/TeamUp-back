package main.java.io.takima.teamupback.post

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import main.java.io.takima.teamupback.discussion.DiscussionRepository
import main.java.io.takima.teamupback.location.LocationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
    private val postDao: PostDao,
    private val locationRepository: LocationRepository,
    private val discussionRepository: DiscussionRepository
) {

    fun findAll(page: Int, size: Int): PostListResponse {
        val offset = page * size
        val posts = postDao.findAllWithPagination(offset, size)
        val total = postDao.count()

        return PostListResponse(
            data = posts.map { PostResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): PostResponse {
        val post = postRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Post", "id", id) }
        return PostResponse.fromEntity(post)
    }

    fun create(request: PostCreateRequest): PostResponse {
        val location = request.locationId?.let {
            locationRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Location", "id", it) }
        }
        val discussion = request.discussionId?.let {
            discussionRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Discussion", "id", it) }
        }

        val post = Post(
            content = request.content,
            imageUrl = request.imageUrl,
            authorId = request.authorId,
            location = location,
            discussion = discussion
        )
        val saved = postRepository.save(post)
        return PostResponse.fromEntity(saved)
    }

    fun update(id: Int, request: PostUpdateRequest): PostResponse {
        val post = postRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Post", "id", id) }

        request.content?.let { post.content = it }
        request.imageUrl?.let { post.imageUrl = it }
        request.locationId?.let { locationId ->
            post.location = locationRepository.findById(locationId)
                .orElseThrow { ResourceNotFoundException("Location", "id", locationId) }
        }

        val saved = postRepository.save(post)
        return PostResponse.fromEntity(saved)
    }

    fun delete(id: Int) {
        if (!postRepository.existsById(id)) {
            throw ResourceNotFoundException("Post", "id", id)
        }
        postRepository.deleteById(id)
    }

    fun findByAuthorId(authorId: Int, page: Int, size: Int): PostListResponse {
        val offset = page * size
        val posts = postDao.findByAuthorIdWithPagination(authorId, offset, size)
        val total = postRepository.findByAuthorId(authorId).size.toLong()

        return PostListResponse(
            data = posts.map { PostResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }
}
