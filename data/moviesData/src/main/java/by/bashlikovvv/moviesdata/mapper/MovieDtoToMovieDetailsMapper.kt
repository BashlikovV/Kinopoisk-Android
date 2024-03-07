package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.base.IMapper
import by.bashlikovvv.core.domain.model.MovieDetails
import by.bashlikovvv.moviesdata.remote.response.MovieDto

class MovieDtoToMovieDetailsMapper : IMapper<MovieDto, MovieDetails> {
    override fun mapFromEntity(entity: MovieDto): MovieDetails {
        val similarMoviesDtoToMovieMapper = SimilarMoviesDtoToMovieMapper()
        val personDtoToPersonMapper = PersonDtoToPersonMapper()

        return MovieDetails(
            id = entity.id,
            year = entity.year,
            rating = RatingDtoToRatingMapper().mapFromEntity(entity.ratingDto),
            videos = entity.videos.trailers?.map { it.url } ?: listOf(),
            type = entity.type,
            logo = entity.logo.url,
            top250 = (entity.top250 ?: 0) > 0,
            budget = BudgetDtoToBudgetMapper().mapFromEntity(entity.budgetDto),
            similarMovies = entity.similarMovies
                ?.map {
                    similarMoviesDtoToMovieMapper.mapFromEntity(it)
                } ?: listOf(),
            shortDescription = entity.shortDescription,
            persons = entity.persons
                ?.map {
                    personDtoToPersonMapper.mapFromEntity(it)
                } ?: listOf(),
            name = entity.name,
            poster = entity.poster.url,
            facts = entity.facts?.map { it.value } ?: listOf(),
            movieLength = entity.movieLength,
            genres = entity.genres?.map { it.name } ?: listOf(),
            watchAbility = entity.watchability.items?.map { it.name } ?: listOf()
        )
    }

    override fun mapToEntity(domain: MovieDetails): MovieDto {
        throw IllegalStateException("Not implemented!")
    }
}