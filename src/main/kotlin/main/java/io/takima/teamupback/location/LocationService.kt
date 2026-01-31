package main.java.io.takima.teamupback.location

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LocationService(
    private val locationRepository: LocationRepository,
    private val locationDao: LocationDao
) {

    fun findAll(page: Int, size: Int): LocationListResponse {
        val offset = page * size
        val locations = locationDao.findAllWithPagination(offset, size)
        val total = locationDao.count()

        return LocationListResponse(
            data = locations.map { LocationResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): LocationResponse {
        val location = locationRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Location", "id", id) }
        return LocationResponse.fromEntity(location)
    }

    fun create(request: LocationCreateRequest): LocationResponse {
        val location = Location(
            name = request.name,
            address = request.address,
            averagePrice = request.averagePrice,
            pictureUrl = request.pictureUrl
        )
        val saved = locationRepository.save(location)
        return LocationResponse.fromEntity(saved)
    }

    fun update(id: Int, request: LocationUpdateRequest): LocationResponse {
        val location = locationRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Location", "id", id) }

        request.name?.let { location.name = it }
        request.address?.let { location.address = it }
        request.averagePrice?.let { location.averagePrice = it }
        request.pictureUrl?.let { location.pictureUrl = it }

        val saved = locationRepository.save(location)
        return LocationResponse.fromEntity(saved)
    }

    fun delete(id: Int) {
        if (!locationRepository.existsById(id)) {
            throw ResourceNotFoundException("Location", "id", id)
        }
        locationRepository.deleteById(id)
    }

    fun searchByName(name: String, page: Int, size: Int): LocationListResponse {
        val offset = page * size
        val locations = locationDao.searchByName(name, offset, size)
        val total = locationRepository.findByNameContainingIgnoreCase(name).size.toLong()

        return LocationListResponse(
            data = locations.map { LocationResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }
}
