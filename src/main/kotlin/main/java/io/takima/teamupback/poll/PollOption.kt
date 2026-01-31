package main.java.io.takima.teamupback.poll

import jakarta.persistence.*
import main.java.io.takima.teamupback.location.Location
import main.java.io.takima.teamupback.user.User
import java.time.LocalDateTime

@Entity
@Table(
    name = "poll_option",
    uniqueConstraints = [UniqueConstraint(columnNames = ["poll_id", "location_id"])]
)
class PollOption(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poll_option_id_seq")
    @SequenceGenerator(name = "poll_option_id_seq", sequenceName = "poll_option_id_seq", allocationSize = 1)
    val id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id")
    var poll: Poll? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    var location: Location? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by_user_id")
    var addedByUser: User? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now()
)
