package main.java.io.takima.teamupback.reaction

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReactionRepository : JpaRepository<Reaction, Int> {
    fun findByMessageId(messageId: Int): List<Reaction>
    fun findByUserId(userId: Int): List<Reaction>
    fun findByMessageIdAndUserId(messageId: Int, userId: Int): List<Reaction>
    fun findByMessageIdAndUserIdAndEmoji(messageId: Int, userId: Int, emoji: String): Reaction?
}
