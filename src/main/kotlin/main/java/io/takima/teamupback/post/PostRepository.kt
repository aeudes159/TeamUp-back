package main.java.io.takima.teamupback.post

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Int> {
    fun findByAuthorId(authorId: Int): List<Post>
    fun findByDiscussionId(discussionId: Int): List<Post>
    fun findByLocationId(locationId: Int): List<Post>
}
