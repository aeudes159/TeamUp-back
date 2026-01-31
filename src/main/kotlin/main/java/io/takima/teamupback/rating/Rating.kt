package main.java.io.takima.teamupback.rating

import jakarta.persistence.*
import main.java.io.takima.teamupback.location.Location
import java.time.LocalDateTime

@Entity
@Table(name = "rating")
class Rating(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_id_seq")
    @SequenceGenerator(name = "rating_id_seq", sequenceName = "rating_id_seq", allocationSize = 1)
    val id: Int? = null,

    @Column(name = "rating_value")
    var ratingValue: Int? = null,

    @Column(name = "user_id")
    var userId: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    var location: Location? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now()
)
