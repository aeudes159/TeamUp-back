package main.java.io.takima.teamupback.activityFeed

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityFeedRepository : JpaRepository<ActivityFeed, Int>
