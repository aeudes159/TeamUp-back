package main.java.io.takima.teamupback.poll

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/polls")
class PollController(
    private val pollService: PollService
) {

    // ============================================
    // Poll Endpoints
    // ============================================

    @GetMapping("/by-discussion/{discussionId}")
    fun findByDiscussionId(
        @PathVariable discussionId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<PollListResponse> {
        return ResponseEntity.ok(pollService.findByDiscussionId(discussionId, page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<PollResponse> {
        return ResponseEntity.ok(pollService.findById(id))
    }

    @PostMapping
    fun create(@RequestBody request: PollCreateRequest): ResponseEntity<PollResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(pollService.create(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody request: PollUpdateRequest,
        @RequestParam userId: Int
    ): ResponseEntity<PollResponse> {
        return ResponseEntity.ok(pollService.update(id, request, userId))
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Int,
        @RequestParam userId: Int
    ): ResponseEntity<Void> {
        pollService.delete(id, userId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/close")
    fun closePoll(
        @PathVariable id: Int,
        @RequestParam userId: Int
    ): ResponseEntity<PollResponse> {
        return ResponseEntity.ok(pollService.closePoll(id, userId))
    }

    // ============================================
    // Poll Option Endpoints
    // ============================================

    @PostMapping("/options")
    fun addOption(@RequestBody request: PollOptionCreateRequest): ResponseEntity<PollOptionResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(pollService.addOption(request))
    }

    @DeleteMapping("/options/{id}")
    fun removeOption(
        @PathVariable id: Int,
        @RequestParam userId: Int
    ): ResponseEntity<Void> {
        pollService.removeOption(id, userId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{pollId}/options")
    fun findOptionsByPollId(@PathVariable pollId: Int): ResponseEntity<List<PollOptionResponse>> {
        return ResponseEntity.ok(pollService.findOptionsByPollId(pollId))
    }

    // ============================================
    // Poll Vote Endpoints
    // ============================================

    @PostMapping("/votes")
    fun vote(@RequestBody request: PollVoteCreateRequest): ResponseEntity<PollVoteResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(pollService.vote(request))
    }

    @DeleteMapping("/votes")
    fun removeVote(
        @RequestParam optionId: Int,
        @RequestParam userId: Int
    ): ResponseEntity<Void> {
        pollService.removeVote(optionId, userId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/options/{optionId}/votes")
    fun findVotesByOptionId(@PathVariable optionId: Int): ResponseEntity<List<PollVoteResponse>> {
        return ResponseEntity.ok(pollService.findVotesByOptionId(optionId))
    }
}
