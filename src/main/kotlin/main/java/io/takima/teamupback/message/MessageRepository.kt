package main.java.io.takima.teamupback.message

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<Message, Int> {
    fun findByDiscussionId(discussionId: Int): List<Message>
    fun findBySenderId(senderId: Int): List<Message>
}
