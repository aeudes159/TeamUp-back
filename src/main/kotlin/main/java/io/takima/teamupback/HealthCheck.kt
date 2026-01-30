package main.java.io.takima.teamupback

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class HealthController {

    @GetMapping("/health")
    fun health(): Map<String, String> {
        return mapOf("status" to "ok")
    }
}