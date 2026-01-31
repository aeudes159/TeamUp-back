package main.java.io.takima.teamupback.message

import jakarta.persistence.*
import main.java.io.takima.teamupback.discussion.Discussion
import java.time.LocalDateTime

@Entity
@Table(name = "message")
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_seq")
    @SequenceGenerator(name = "message_id_seq", sequenceName = "message_id_seq", allocationSize = 1)
    val id: Int? = null,

    @Column(name = "content")
    var content: String? = null,

    @Column(name = "image_url")
    var imageUrl: String? = null,

    @Column(name = "sent_at")
    val sentAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "sender_id")
    var senderId: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_id")
    var discussion: Discussion? = null
)
