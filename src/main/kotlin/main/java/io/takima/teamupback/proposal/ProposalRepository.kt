package main.java.io.takima.teamupback.proposal

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProposalRepository : JpaRepository<Proposal, Int> {
    fun findByDiscussionId(discussionId: Int): List<Proposal>
}
