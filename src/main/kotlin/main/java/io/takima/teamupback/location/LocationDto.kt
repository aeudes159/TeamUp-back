package main.java.io.takima.teamupback.location

import java.math.BigDecimal

// Request DTOs
data class LocationCreateRequest(
    val name: String? = null,
    val address: String? = null,
    val averagePrice: BigDecimal? = null,
    val pictureUrl: String? = null
)

data class LocationUpdateRequest(
    val name: String? = null,
    val address: String? = null,
    val averagePrice: BigDecimal? = null,
    val pictureUrl: String? = null
)

// Response DTOs
data class LocationResponse(
    val id: Int?,
    val name: String?,
    val address: String?,
    val averagePrice: BigDecimal?,
    val pictureUrl: String?
) {
    companion object {
        fun fromEntity(entity: Location): LocationResponse {
            return LocationResponse(
                id = entity.id,
                name = entity.name,
                address = entity.address,
                averagePrice = entity.averagePrice,
                pictureUrl = entity.pictureUrl
            )
        }
    }
}

data class LocationListResponse(
    val data: List<LocationResponse>,
    val total: Long,
    val page: Int,
    val size: Int
)
