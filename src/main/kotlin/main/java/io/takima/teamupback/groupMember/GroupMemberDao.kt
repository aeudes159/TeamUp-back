package main.java.io.takima.teamupback.groupmember

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class GroupMemberDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<GroupMember> {
        return entityManager.createQuery(
            "SELECT gm FROM GroupMember gm ORDER BY gm.joinedAt DESC",
            GroupMember::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(gm) FROM GroupMember gm",
            Long::class.javaObjectType
        ).singleResult
    }

    fun findByGroupId(groupId: Int, offset: Int, limit: Int): List<GroupMember> {
        return entityManager.createQuery(
            "SELECT gm FROM GroupMember gm WHERE gm.id.groupId = :groupId ORDER BY gm.joinedAt DESC",
            GroupMember::class.java
        )
            .setParameter("groupId", groupId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun findByUserId(userId: Int, offset: Int, limit: Int): List<GroupMember> {
        return entityManager.createQuery(
            "SELECT gm FROM GroupMember gm WHERE gm.id.userId = :userId ORDER BY gm.joinedAt DESC",
            GroupMember::class.java
        )
            .setParameter("userId", userId)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }
}
