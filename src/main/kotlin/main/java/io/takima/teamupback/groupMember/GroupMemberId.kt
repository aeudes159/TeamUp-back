package main.java.io.takima.teamupback.groupMember

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class GroupMemberId(
    @Column(name = "group_id")
    val groupId: Int = 0,

    @Column(name = "user_id")
    val userId: Int = 0
) : Serializable
