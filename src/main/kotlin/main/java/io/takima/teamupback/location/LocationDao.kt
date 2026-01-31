package main.java.io.takima.teamupback.location

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class LocationDao(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun findAllWithPagination(offset: Int, limit: Int): List<Location> {
        return entityManager.createQuery(
            "SELECT l FROM Location l ORDER BY l.name",
            Location::class.java
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(l) FROM Location l",
            Long::class.javaObjectType
        ).singleResult
    }

    fun searchByName(name: String, offset: Int, limit: Int): List<Location> {
        return entityManager.createQuery(
            "SELECT l FROM Location l WHERE LOWER(l.name) LIKE LOWER(:name) ORDER BY l.name",
            Location::class.java
        )
            .setParameter("name", "%$name%")
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }
}
