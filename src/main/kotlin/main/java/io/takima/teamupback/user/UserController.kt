package main.java.io.takima.teamupback.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<UserListResponse> {
        return ResponseEntity.ok(userService.findAll(page, size))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.findById(id))
    }

    @PostMapping
    fun create(@RequestBody request: UserCreateRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody request: UserUpdateRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        userService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun searchByName(
        @RequestParam name: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<UserListResponse> {
        return ResponseEntity.ok(userService.searchByName(name, page, size))
    }

    @GetMapping("/by-phone")
    fun findByPhoneNumber(@RequestParam phoneNumber: String): ResponseEntity<UserResponse> {
        val user = userService.findByPhoneNumber(phoneNumber)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
