package main.java.io.takima.teamupback.group

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/groups")
class GroupController(
    private val groupService: GroupService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<GroupListResponse> {
        return ResponseEntity.ok(groupService.findAll(page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<GroupResponse> {
        return ResponseEntity.ok(groupService.findById(id))
    }

    @PostMapping
    fun create(@RequestBody request: GroupCreateRequest): ResponseEntity<GroupResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.create(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody request: GroupUpdateRequest
    ): ResponseEntity<GroupResponse> {
        return ResponseEntity.ok(groupService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        groupService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/public")
    fun findPublicGroups(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<GroupListResponse> {
        return ResponseEntity.ok(groupService.findPublicGroups(page, size))
    }
}
