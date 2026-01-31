package main.java.io.takima.teamupback.reaction

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reactions")
class ReactionController(
    private val reactionService: ReactionService
) {
    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<ReactionListResponse> {
        return ResponseEntity.ok(reactionService.findAll(page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<ReactionResponse> {
        return ResponseEntity.ok(reactionService.findById(id))
    }

    @PostMapping
    fun create(@RequestBody request: ReactionCreateRequest): ResponseEntity<ReactionResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(reactionService.create(request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        reactionService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/by-message/{messageId}")
    fun findByMessageId(
        @PathVariable messageId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "50") size: Int
    ): ResponseEntity<ReactionListResponse> {
        return ResponseEntity.ok(reactionService.findByMessageId(messageId, page, size))
    }

    @GetMapping("/by-comment/{commentId}")
    fun findByCommentId(
        @PathVariable commentId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "50") size: Int
    ): ResponseEntity<ReactionListResponse> {
        return ResponseEntity.ok(reactionService.findByCommentId(commentId, page, size))
    }

    @GetMapping("/by-user/{userId}")
    fun findByUserId(
        @PathVariable userId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<ReactionListResponse> {
        return ResponseEntity.ok(reactionService.findByUserId(userId, page, size))
    }
}
