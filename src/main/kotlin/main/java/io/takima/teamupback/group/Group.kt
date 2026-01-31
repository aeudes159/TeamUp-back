package main.java.io.takima.teamupback.group

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "\"group\"")
class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_id_seq")
    @SequenceGenerator(name = "group_id_seq", sequenceName = "group_id_seq", allocationSize = 1)
    val id: Int? = null,

    @Column(name = "name", nullable = false, length = 150)
    var name: String,

    @Column(name = "cover_picture_url")
    var coverPictureUrl: String? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "is_public", nullable = false)
    var isPublic: Boolean
)
