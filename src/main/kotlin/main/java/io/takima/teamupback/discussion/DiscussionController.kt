package main.java.io.takima.teamupback.discussion

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/discussions")
class DiscussionController(
    private val discussionService: DiscussionService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<DiscussionListResponse> {
        return ResponseEntity.ok(discussionService.findAll(page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<DiscussionResponse> {
        return ResponseEntity.ok(discussionService.findById(id))
    }

    @PostMapping
    fun create(@RequestBody request: DiscussionCreateRequest): ResponseEntity<DiscussionResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(discussionService.create(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody request: DiscussionUpdateRequest
    ): ResponseEntity<DiscussionResponse> {
        return ResponseEntity.ok(discussionService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        discussionService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/by-group/{groupId}")
    fun findByGroupId(
        @PathVariable groupId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<DiscussionListResponse> {
        return ResponseEntity.ok(discussionService.findByGroupId(groupId, page, size))
    }
}
