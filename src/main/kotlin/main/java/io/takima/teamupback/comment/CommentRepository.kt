package main.java.io.takima.teamupback.comment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Int> {
    fun findByPostId(postId: Int): List<Comment>
    fun findByAuthorId(authorId: Int): List<Comment>
    fun countByPostId(postId: Int): Long
}
