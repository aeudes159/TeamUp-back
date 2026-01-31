package main.java.io.takima.teamupback.proposal

import jakarta.persistence.*
import main.java.io.takima.teamupback.discussion.Discussion
import java.time.LocalDateTime

@Entity
@Table(name = "proposal")
class Proposal(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proposal_id_seq")
    @SequenceGenerator(name = "proposal_id_seq", sequenceName = "proposal_id_seq", allocationSize = 1)
    val id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_id")
    var discussion: Discussion? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now()
)
