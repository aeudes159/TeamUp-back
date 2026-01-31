package main.java.io.takima.teamupback.poll

import jakarta.persistence.*
import main.java.io.takima.teamupback.user.User
import java.time.LocalDateTime

@Entity
@Table(
    name = "poll_vote",
    uniqueConstraints = [UniqueConstraint(columnNames = ["poll_option_id", "user_id"])]
)
class PollVote(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poll_vote_id_seq")
    @SequenceGenerator(name = "poll_vote_id_seq", sequenceName = "poll_vote_id_seq", allocationSize = 1)
    val id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_option_id")
    var pollOption: PollOption? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now()
)
