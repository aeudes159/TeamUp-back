package main.java.io.takima.teamupback.comment

import jakarta.persistence.*
import main.java.io.takima.teamupback.post.Post
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
    @SequenceGenerator(name = "comment_id_seq", sequenceName = "comment_id_seq", allocationSize = 1)
    val id: Int? = null,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "author_id", nullable = false)
    var authorId: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post,

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now()
)
