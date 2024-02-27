package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.domain.model.IMapper
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.model.MovieDetails
import by.bashlikovvv.moviesdata.local.model.MovieDetailsEntity

class MovieDetailsEntityToMovieDetailsMapper(
    private val movie: Movie
) : IMapper<MovieDetailsEntity, MovieDetails> {
    override fun mapFromEntity(entity: MovieDetailsEntity): MovieDetails {
        return MovieDetails(
            id = entity.id,
            shortDescription = entity.description,
            name = movie.name,
            poster = movie.poster,
            genres = movie.genres,
            watchAbility = entity.watchAbility
        )
    }

    override fun mapToEntity(domain: MovieDetails): MovieDetailsEntity {
        return MovieDetailsEntity(
            id = domain.id,
            persons = domain.persons?.map { it.id } ?: listOf(),
            description = domain.shortDescription,
            similarMovies = domain.similarMovies?.map { it.id } ?: listOf(),
            watchAbility = domain.watchAbility,
            trailers = domain.videos ?: listOf(),
            keyMovieId = movie.id
        )
    }
}