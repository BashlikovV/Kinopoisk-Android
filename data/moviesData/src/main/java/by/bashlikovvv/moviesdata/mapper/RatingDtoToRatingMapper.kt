package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.base.IMapper
import by.bashlikovvv.core.domain.model.Rating
import by.bashlikovvv.moviesdata.remote.response.RatingDto

class RatingDtoToRatingMapper : IMapper<RatingDto, Rating> {
    override fun mapFromEntity(entity: RatingDto): Rating {
        return Rating(
            imdb = entity.imdb,
            kp = entity.kp,
            await = entity.await,
            russianFilmCritics = entity.russianFilmCritics,
            filmCritics = entity.filmCritics
        )
    }

    override fun mapToEntity(domain: Rating): RatingDto {
        return RatingDto(
            imdb = domain.imdb ?: 0.0,
            kp = domain.kp ?: 0.0,
            await = domain.await,
            russianFilmCritics = domain.russianFilmCritics ?: 0.0,
            filmCritics = domain.filmCritics ?: 0.0
        )
    }
}