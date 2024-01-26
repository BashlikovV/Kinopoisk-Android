package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.domain.model.IMapper
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.moviesdata.remote.response.MovieDto

class MovieDtoToMovieMapper : IMapper<MovieDto, Movie> {
    override fun mapFromEntity(entity: MovieDto): Movie {
        return Movie(
            id = entity.id,
            name = entity.name,
            description = entity.shortDescription,
            poster = entity.poster.previewUrl,
            age = entity.ageRating.toInt(),
            genres = (entity.genres ?: listOf()).map { it.name }
        )
    }

    override fun mapToEntity(domain: Movie): MovieDto {
        throw IllegalStateException()
    }
}