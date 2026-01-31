package main.java.io.takima.teamupback.common.dao

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import kotlin.reflect.KClass

/**
 * Abstract base DAO providing common pagination methods.
 * Reduces code duplication across all DAOs.
 * 
 * Usage:
 * ```kotlin
 * @Component
 * class UserDao : BaseDao<User>(User::class) {
 *     // Custom methods here - base methods inherited
 * }
 * ```
 * 
 * Subclasses can override getDefaultOrderBy() to customize sorting.
 */
abstract class BaseDao<E : Any>(
    private val entityClass: KClass<E>
) {
    @PersistenceContext
    protected lateinit var entityManager: EntityManager

    /**
     * Get the entity class for queries
     */
    protected fun getEntityClass(): Class<E> = entityClass.java

    /**
     * Get the simple entity name for JPQL queries
     */
    protected fun getEntityName(): String = entityClass.simpleName ?: entityClass.java.simpleName

    /**
     * Override this to customize default ordering.
     * Return empty string for no ordering.
     */
    protected open fun getDefaultOrderBy(): String = ""

    /**
     * Find all entities with pagination
     */
    open fun findAllWithPagination(offset: Int, limit: Int): List<E> {
        val orderBy = getDefaultOrderBy()
        val orderClause = if (orderBy.isNotBlank()) " ORDER BY $orderBy" else ""
        
        return entityManager.createQuery(
            "SELECT e FROM ${getEntityName()} e$orderClause",
            getEntityClass()
        )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    /**
     * Count all entities
     */
    open fun count(): Long {
        return entityManager.createQuery(
            "SELECT COUNT(e) FROM ${getEntityName()} e",
            Long::class.javaObjectType
        ).singleResult
    }

    /**
     * Count entities with a custom where clause
     */
    protected fun countWhere(whereClause: String, parameters: Map<String, Any> = emptyMap()): Long {
        val query = entityManager.createQuery(
            "SELECT COUNT(e) FROM ${getEntityName()} e WHERE $whereClause",
            Long::class.javaObjectType
        )
        parameters.forEach { (name, value) -> query.setParameter(name, value) }
        return query.singleResult
    }

    /**
     * Find entities with pagination and a custom where clause
     */
    protected fun findWithPagination(
        whereClause: String,
        parameters: Map<String, Any>,
        offset: Int,
        limit: Int,
        orderBy: String? = null
    ): List<E> {
        val orderClause = (orderBy ?: getDefaultOrderBy()).let { 
            if (it.isNotBlank()) " ORDER BY $it" else "" 
        }
        
        val query = entityManager.createQuery(
            "SELECT e FROM ${getEntityName()} e WHERE $whereClause$orderClause",
            getEntityClass()
        )
        parameters.forEach { (name, value) -> query.setParameter(name, value) }
        
        return query
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList
    }

    /**
     * Find entities by a single field with pagination
     * Common pattern: findByDiscussionId, findByUserId, etc.
     */
    protected fun findByFieldWithPagination(
        fieldName: String,
        fieldValue: Any,
        offset: Int,
        limit: Int,
        orderBy: String? = null
    ): List<E> {
        return findWithPagination(
            whereClause = "e.$fieldName = :value",
            parameters = mapOf("value" to fieldValue),
            offset = offset,
            limit = limit,
            orderBy = orderBy
        )
    }

    /**
     * Count entities by a single field
     */
    protected fun countByField(fieldName: String, fieldValue: Any): Long {
        return countWhere("e.$fieldName = :value", mapOf("value" to fieldValue))
    }

    /**
     * Search by a text field (case-insensitive LIKE)
     */
    protected fun searchByFieldWithPagination(
        fieldName: String,
        searchValue: String,
        offset: Int,
        limit: Int,
        orderBy: String? = null
    ): List<E> {
        return findWithPagination(
            whereClause = "LOWER(e.$fieldName) LIKE LOWER(:search)",
            parameters = mapOf("search" to "%$searchValue%"),
            offset = offset,
            limit = limit,
            orderBy = orderBy
        )
    }
}
