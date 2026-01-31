package main.java.io.takima.teamupback.poll

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PollRepository : JpaRepository<Poll, Int> {
    fun findByDiscussionId(discussionId: Int): List<Poll>
    fun findByCreatorId(creatorId: Int): List<Poll>
    fun findByIsActive(isActive: Boolean): List<Poll>
}

@Repository
interface PollOptionRepository : JpaRepository<PollOption, Int> {
    fun findByPollId(pollId: Int): List<PollOption>
    fun findByLocationId(locationId: Int): List<PollOption>
    fun existsByPollIdAndLocationId(pollId: Int, locationId: Int): Boolean
}

@Repository
interface PollVoteRepository : JpaRepository<PollVote, Int> {
    fun findByPollOptionId(pollOptionId: Int): List<PollVote>
    fun findByUserId(userId: Int): List<PollVote>
    fun findByPollOptionIdAndUserId(pollOptionId: Int, userId: Int): PollVote?
    fun existsByPollOptionIdAndUserId(pollOptionId: Int, userId: Int): Boolean
    fun deleteByPollOptionIdAndUserId(pollOptionId: Int, userId: Int)
}
