package main.java.io.takima.teamupback.rating

import main.java.io.takima.teamupback.common.exception.ResourceNotFoundException
import main.java.io.takima.teamupback.location.LocationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RatingService(
    private val ratingRepository: RatingRepository,
    private val ratingDao: RatingDao,
    private val locationRepository: LocationRepository
) {

    fun findAll(page: Int, size: Int): RatingListResponse {
        val offset = page * size
        val ratings = ratingDao.findAllWithPagination(offset, size)
        val total = ratingDao.count()

        return RatingListResponse(
            data = ratings.map { RatingResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun findById(id: Int): RatingResponse {
        val rating = ratingRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Rating", "id", id) }
        return RatingResponse.fromEntity(rating)
    }

    fun create(request: RatingCreateRequest): RatingResponse {
        val location = request.locationId?.let {
            locationRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Location", "id", it) }
        }

        val rating = Rating(
            ratingValue = request.ratingValue,
            userId = request.userId,
            location = location
        )
        val saved = ratingRepository.save(rating)
        return RatingResponse.fromEntity(saved)
    }

    fun update(id: Int, request: RatingUpdateRequest): RatingResponse {
        val rating = ratingRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Rating", "id", id) }

        request.ratingValue?.let { rating.ratingValue = it }

        val saved = ratingRepository.save(rating)
        return RatingResponse.fromEntity(saved)
    }

    fun delete(id: Int) {
        if (!ratingRepository.existsById(id)) {
            throw ResourceNotFoundException("Rating", "id", id)
        }
        ratingRepository.deleteById(id)
    }

    fun findByLocationId(locationId: Int, page: Int, size: Int): RatingListResponse {
        val offset = page * size
        val ratings = ratingDao.findByLocationIdWithPagination(locationId, offset, size)
        val total = ratingRepository.findByLocationId(locationId).size.toLong()

        return RatingListResponse(
            data = ratings.map { RatingResponse.fromEntity(it) },
            total = total,
            page = page,
            size = size
        )
    }

    fun getAverageRatingForLocation(locationId: Int): Double? {
        return ratingDao.getAverageRatingForLocation(locationId)
    }
}
