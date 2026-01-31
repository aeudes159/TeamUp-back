package main.java.io.takima.teamupback.reaction

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reaction")
class Reaction(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reaction_id_seq")
    @SequenceGenerator(name = "reaction_id_seq", sequenceName = "reaction_id_seq", allocationSize = 1)
    val id: Int? = null,

    @Column(name = "emoji", nullable = false, length = 10)
    var emoji: String,

    @Column(name = "user_id", nullable = false)
    var userId: Int,

    @Column(name = "message_id")
    var messageId: Int? = null,

    @Column(name = "comment_id")
    var commentId: Int? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now()
)
