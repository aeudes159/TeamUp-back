package main.java.io.takima.teamupback.user

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class UserDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<User> {
        return entityManager.createQuery(
            "SELECT u FROM User u ORDER BY u.lastName, u.firstName",
            User::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(u) FROM User u",
            Long::class.javaObjectType
        ).singleResult
    }

    fun searchByName(name: String, offset: Int, limit: Int): List<User> {
        return entityManager.createQuery(
            "SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(:name) OR LOWER(u.lastName) LIKE LOWER(:name) ORDER BY u.lastName, u.firstName",
            User::class.java
        )
            .setParameter("name", "%$name%")
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }
}
