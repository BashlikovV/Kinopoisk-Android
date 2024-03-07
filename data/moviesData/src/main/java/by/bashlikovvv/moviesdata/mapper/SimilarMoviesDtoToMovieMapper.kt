package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.base.IMapper
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.moviesdata.remote.response.SimilarMoviesItemDto

class SimilarMoviesDtoToMovieMapper : IMapper<SimilarMoviesItemDto, Movie> {
    override fun mapFromEntity(entity: SimilarMoviesItemDto): Movie {
        return Movie(
            id = entity.id.toLong(),
            name = entity.name,
            poster = entity.poster.url
        )
    }

    override fun mapToEntity(domain: Movie): SimilarMoviesItemDto {
        throw IllegalStateException("Not implemented!")
    }
}