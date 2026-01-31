package main.java.io.takima.teamupback.proposal

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/proposals")
class ProposalController(
    private val proposalService: ProposalService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<ProposalListResponse> {
        return ResponseEntity.ok(proposalService.findAll(page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<ProposalResponse> {
        return ResponseEntity.ok(proposalService.findById(id))
    }

    @PostMapping
    fun create(@RequestBody request: ProposalCreateRequest): ResponseEntity<ProposalResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(proposalService.create(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody request: ProposalUpdateRequest
    ): ResponseEntity<ProposalResponse> {
        return ResponseEntity.ok(proposalService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        proposalService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/by-discussion/{discussionId}")
    fun findByDiscussionId(
        @PathVariable discussionId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<ProposalListResponse> {
        return ResponseEntity.ok(proposalService.findByDiscussionId(discussionId, page, size))
    }
}
