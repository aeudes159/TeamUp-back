package main.java.io.takima.teamupback.rating

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ratings")
class RatingController(
    private val ratingService: RatingService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<RatingListResponse> {
        return ResponseEntity.ok(ratingService.findAll(page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<RatingResponse> {
        return ResponseEntity.ok(ratingService.findById(id))
    }

    @PostMapping
    fun create(@RequestBody request: RatingCreateRequest): ResponseEntity<RatingResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody request: RatingUpdateRequest
    ): ResponseEntity<RatingResponse> {
        return ResponseEntity.ok(ratingService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        ratingService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/by-location/{locationId}")
    fun findByLocationId(
        @PathVariable locationId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<RatingListResponse> {
        return ResponseEntity.ok(ratingService.findByLocationId(locationId, page, size))
    }

    @GetMapping("/average/location/{locationId}")
    fun getAverageRatingForLocation(@PathVariable locationId: Int): ResponseEntity<Map<String, Double?>> {
        val average = ratingService.getAverageRatingForLocation(locationId)
        return ResponseEntity.ok(mapOf("averageRating" to average))
    }
}
