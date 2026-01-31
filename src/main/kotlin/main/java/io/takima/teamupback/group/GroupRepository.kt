package main.java.io.takima.teamupback.group

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Int> {
    fun findByIsPublic(isPublic: Boolean): List<Group>
    fun findByNameContainingIgnoreCase(name: String): List<Group>
}
