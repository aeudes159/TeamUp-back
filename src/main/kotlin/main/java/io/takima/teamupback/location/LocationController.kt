package main.java.io.takima.teamupback.location

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/locations")
class LocationController(
    private val locationService: LocationService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<LocationListResponse> {
        return ResponseEntity.ok(locationService.findAll(page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<LocationResponse> {
        return ResponseEntity.ok(locationService.findById(id))
    }

    @PostMapping
    fun create(@RequestBody request: LocationCreateRequest): ResponseEntity<LocationResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.create(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody request: LocationUpdateRequest
    ): ResponseEntity<LocationResponse> {
        return ResponseEntity.ok(locationService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        locationService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun searchByName(
        @RequestParam name: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<LocationListResponse> {
        return ResponseEntity.ok(locationService.searchByName(name, page, size))
    }
}
