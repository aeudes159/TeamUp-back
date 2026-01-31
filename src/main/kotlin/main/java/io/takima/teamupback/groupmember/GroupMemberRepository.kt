package main.java.io.takima.teamupback.groupmember

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupMemberRepository : JpaRepository<GroupMember, GroupMemberId> {
    fun findByIdGroupId(groupId: Int): List<GroupMember>
    fun findByIdUserId(userId: Int): List<GroupMember>
}
