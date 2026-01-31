package main.java.io.takima.teamupback.location

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : JpaRepository<Location, Int> {
    fun findByNameContainingIgnoreCase(name: String): List<Location>
}
