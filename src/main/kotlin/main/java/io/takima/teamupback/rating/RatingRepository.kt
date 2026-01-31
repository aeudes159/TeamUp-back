package main.java.io.takima.teamupback.rating

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RatingRepository : JpaRepository<Rating, Int> {
    fun findByUserId(userId: Int): List<Rating>
    fun findByLocationId(locationId: Int): List<Rating>
}
