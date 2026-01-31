package main.java.io.takima.teamupback.post

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<PostListResponse> {
        return ResponseEntity.ok(postService.findAll(page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<PostResponse> {
        return ResponseEntity.ok(postService.findById(id))
    }

    @PostMapping
    fun create(@RequestBody request: PostCreateRequest): ResponseEntity<PostResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody request: PostUpdateRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.ok(postService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        postService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/by-author/{authorId}")
    fun findByAuthorId(
        @PathVariable authorId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<PostListResponse> {
        return ResponseEntity.ok(postService.findByAuthorId(authorId, page, size))
    }
}
