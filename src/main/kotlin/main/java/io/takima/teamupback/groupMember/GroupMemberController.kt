package main.java.io.takima.teamupback.groupmember

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/group-members")
class GroupMemberController(
    private val groupMemberService: GroupMemberService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<GroupMemberListResponse> {
        return ResponseEntity.ok(groupMemberService.findAll(page, size))
    }

    @GetMapping("/{groupId}/{userId}")
    fun findById(
        @PathVariable groupId: Int,
        @PathVariable userId: Int
    ): ResponseEntity<GroupMemberResponse> {
        return ResponseEntity.ok(groupMemberService.findById(groupId, userId))
    }

    @PostMapping
    fun create(@RequestBody request: GroupMemberCreateRequest): ResponseEntity<GroupMemberResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupMemberService.create(request))
    }

    @DeleteMapping("/{groupId}/{userId}")
    fun delete(
        @PathVariable groupId: Int,
        @PathVariable userId: Int
    ): ResponseEntity<Void> {
        groupMemberService.delete(groupId, userId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/by-group/{groupId}")
    fun findByGroupId(
        @PathVariable groupId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<GroupMemberListResponse> {
        return ResponseEntity.ok(groupMemberService.findByGroupId(groupId, page, size))
    }

    @GetMapping("/by-user/{userId}")
    fun findByUserId(
        @PathVariable userId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<GroupMemberListResponse> {
        return ResponseEntity.ok(groupMemberService.findByUserId(userId, page, size))
    }
}
