package main.java.io.takima.teamupback.user

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val userDao: UserDao
) {

    fun findAll(page: Int, size: Int): UserListResponse {
        val offset = page * size
        val users = userDao.findAllWithPagination(offset, size)
        val total = userDao.count()

        return UserListResponse(
            data = users.map { UserResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User", "id", id) }
        return UserResponse.fromEntity(user)
    }

    fun create(request: UserCreateRequest): UserResponse {
        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            age = request.age,
            phoneNumber = request.phoneNumber,
            address = request.address,
            profilePictureUrl = request.profilePictureUrl
        )
        val saved = userRepository.save(user)
        return UserResponse.fromEntity(saved)
    }

    fun update(id: Int, request: UserUpdateRequest): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User", "id", id) }

        request.firstName?.let { user.firstName = it }
        request.lastName?.let { user.lastName = it }
        request.age?.let { user.age = it }
        request.phoneNumber?.let { user.phoneNumber = it }
        request.address?.let { user.address = it }
        request.profilePictureUrl?.let { user.profilePictureUrl = it }

        val saved = userRepository.save(user)
        return UserResponse.fromEntity(saved)
    }

    fun delete(id: Int) {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException("User", "id", id)
        }
        userRepository.deleteById(id)
    }

    fun searchByName(name: String, page: Int, size: Int): UserListResponse {
        val offset = page * size
        val users = userDao.searchByName(name, offset, size)
        val total = users.size.toLong()

        return UserListResponse(
            data = users.map { UserResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findByPhoneNumber(phoneNumber: String): UserResponse? {
        val user = userRepository.findByPhoneNumber(phoneNumber)
        return user?.let { UserResponse.fromEntity(it) }
    }
}
