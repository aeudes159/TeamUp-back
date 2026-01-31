package main.java.io.takima.teamupback.discussion

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DiscussionRepository : JpaRepository<Discussion, Int> {
    fun findByGroupId(groupId: Int): List<Discussion>
}
