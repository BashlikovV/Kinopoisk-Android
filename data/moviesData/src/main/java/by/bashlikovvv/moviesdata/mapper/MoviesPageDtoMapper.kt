package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.domain.model.IMapper
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.moviesdata.remote.response.MovieDto

class MoviesPageDtoMapper : IMapper<List<MovieDto>, List<Movie>> {
    override fun mapFromEntity(entity: List<MovieDto>): List<Movie> {
        val mapper = MovieDtoToMovieMapper(false)

        return entity.map { mapper.mapFromEntity(it) }
    }

    override fun mapToEntity(domain: List<Movie>): List<MovieDto> {
        val mapper = MovieDtoToMovieMapper(false)

        return domain.map { mapper.mapToEntity(it) }
    }
}