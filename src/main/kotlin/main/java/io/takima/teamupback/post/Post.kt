package main.java.io.takima.teamupback.post

import jakarta.persistence.*
import main.java.io.takima.teamupback.discussion.Discussion
import main.java.io.takima.teamupback.location.Location
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
    @SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
    val id: Int? = null,

    @Column(name = "content")
    var content: String? = null,

    @Column(name = "image_url")
    var imageUrl: String? = null,

    @Column(name = "posted_at")
    val postedAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "author_id")
    var authorId: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    var location: Location? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_id")
    var discussion: Discussion? = null
)
