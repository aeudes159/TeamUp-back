package main.java.io.takima.teamupback.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByFirstNameContainingIgnoreCase(firstName: String): List<User>
    fun findByLastNameContainingIgnoreCase(lastName: String): List<User>
    fun findByPhoneNumber(phoneNumber: String): User?
}
