package main.java.io.takima.teamupback.comment

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
class CommentController(
    private val commentService: CommentService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<CommentListResponse> {
        return ResponseEntity.ok(commentService.findAll(page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.findById(id))
    }

    @GetMapping("/by-post/{postId}")
    fun findByPostId(
        @PathVariable postId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "50") size: Int
    ): ResponseEntity<CommentListResponse> {
        return ResponseEntity.ok(commentService.findByPostId(postId, page, size))
    }

    @GetMapping("/count/by-post/{postId}")
    fun countByPostId(@PathVariable postId: Int): ResponseEntity<Long> {
        return ResponseEntity.ok(commentService.countByPostId(postId))
    }

    @PostMapping
    fun create(@RequestBody request: CommentCreateRequest): ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody request: CommentUpdateRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        commentService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
