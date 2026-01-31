package main.java.io.takima.teamupback.poll

import jakarta.persistence.*
import main.java.io.takima.teamupback.discussion.Discussion
import main.java.io.takima.teamupback.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "poll")
class Poll(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poll_id_seq")
    @SequenceGenerator(name = "poll_id_seq", sequenceName = "poll_id_seq", allocationSize = 1)
    val id: Int? = null,

    @Column(name = "title", length = 255, nullable = false)
    var title: String = "",

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_id")
    var discussion: Discussion? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    var creator: User? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "is_active")
    var isActive: Boolean = true,

    @Column(name = "closed_at")
    var closedAt: LocalDateTime? = null
)
