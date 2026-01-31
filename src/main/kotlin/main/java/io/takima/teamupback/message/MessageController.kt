package main.java.io.takima.teamupback.message

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/messages")
class MessageController(
    private val messageService: MessageService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<MessageListResponse> {
        return ResponseEntity.ok(messageService.findAll(page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<MessageResponse> {
        return ResponseEntity.ok(messageService.findById(id))
    }

    @PostMapping
    fun create(@RequestBody request: MessageCreateRequest): ResponseEntity<MessageResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody request: MessageUpdateRequest
    ): ResponseEntity<MessageResponse> {
        return ResponseEntity.ok(messageService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        messageService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/by-discussion/{discussionId}")
    fun findByDiscussionId(
        @PathVariable discussionId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<MessageListResponse> {
        return ResponseEntity.ok(messageService.findByDiscussionId(discussionId, page, size))
    }
}
