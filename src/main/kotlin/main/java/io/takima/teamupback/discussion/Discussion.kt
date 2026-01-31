package main.java.io.takima.teamupback.discussion

import jakarta.persistence.*
import main.java.io.takima.teamupback.group.Group
import java.time.LocalDateTime

@Entity
@Table(name = "discussion")
class Discussion(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discussion_id_seq")
    @SequenceGenerator(name = "discussion_id_seq", sequenceName = "discussion_id_seq", allocationSize = 1)
    val id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    var group: Group? = null,

    @Column(name = "background_image_url")
    var backgroundImageUrl: String? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now()
)
