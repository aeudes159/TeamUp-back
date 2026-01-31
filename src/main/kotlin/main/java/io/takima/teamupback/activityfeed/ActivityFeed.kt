package main.java.io.takima.teamupback.activityfeed

import jakarta.persistence.*

@Entity
@Table(name = "activity_feed")
class ActivityFeed(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_feed_id_seq")
    @SequenceGenerator(name = "activity_feed_id_seq", sequenceName = "activity_feed_id_seq", allocationSize = 1)
    val id: Int? = null
)
