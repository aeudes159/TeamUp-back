package main.java.io.takima.teamupback.activityFeed

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/activity-feeds")
class ActivityFeedController(
    private val activityFeedService: ActivityFeedService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<ActivityFeedListResponse> {
        return ResponseEntity.ok(activityFeedService.findAll(page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<ActivityFeedResponse> {
        return ResponseEntity.ok(activityFeedService.findById(id))
    }

    @PostMapping
    fun create(@RequestBody request: ActivityFeedCreateRequest): ResponseEntity<ActivityFeedResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(activityFeedService.create(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody request: ActivityFeedUpdateRequest
    ): ResponseEntity<ActivityFeedResponse> {
        return ResponseEntity.ok(activityFeedService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        activityFeedService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
