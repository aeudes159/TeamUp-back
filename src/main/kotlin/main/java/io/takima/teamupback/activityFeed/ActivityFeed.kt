package main.java.io.takima.teamupback.activityFeed

import jakarta.persistence.*
import main.java.io.takima.teamupback.post.Post

@Entity
@Table(name = "activity_feed")
class ActivityFeed(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_feed_id_seq")
    @SequenceGenerator(name = "activity_feed_id_seq", sequenceName = "activity_feed_id_seq", allocationSize = 1)
    val id: Int? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "activity_feed_post",
        joinColumns = [JoinColumn(name = "activity_feed_id")],
        inverseJoinColumns = [JoinColumn(name = "post_id")]
    )
    var posts: MutableSet<Post> = mutableSetOf()
)
