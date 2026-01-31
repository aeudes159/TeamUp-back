package main.java.io.takima.teamupback.groupMember

import jakarta.persistence.*
import main.java.io.takima.teamupback.group.Group
import java.time.LocalDateTime

@Entity
@Table(name = "group_member")
class GroupMember(
    @EmbeddedId
    val id: GroupMemberId = GroupMemberId(),

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    var group: Group? = null,

    @Column(name = "user_id", insertable = false, updatable = false)
    val userId: Int? = null,

    @Column(name = "joined_at")
    val joinedAt: LocalDateTime? = LocalDateTime.now()
)
