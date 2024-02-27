package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.domain.model.IMapper
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
            imdb = domain.imdb,
            kp = domain.kp,
            await = domain.await,
            russianFilmCritics = domain.russianFilmCritics,
            filmCritics = domain.filmCritics
        )
    }
}