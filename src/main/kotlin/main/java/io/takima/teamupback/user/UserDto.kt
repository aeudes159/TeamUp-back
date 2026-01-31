package main.java.io.takima.teamupback.user

// Request DTOs
data class UserCreateRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val age: Int? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val profilePictureUrl: String? = null
)

data class UserUpdateRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val age: Int? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val profilePictureUrl: String? = null
)

// Response DTOs
data class UserResponse(
    val id: Int?,
    val firstName: String?,
    val lastName: String?,
    val age: Int?,
    val phoneNumber: String?,
    val address: String?,
    val profilePictureUrl: String?
) {
    companion object {
        fun fromEntity(entity: User): UserResponse {
            return UserResponse(
                id = entity.id,
                firstName = entity.firstName,
                lastName = entity.lastName,
                age = entity.age,
                phoneNumber = entity.phoneNumber,
                address = entity.address,
                profilePictureUrl = entity.profilePictureUrl
            )
        }
    }
}

data class UserListResponse(
    val data: List<UserResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
