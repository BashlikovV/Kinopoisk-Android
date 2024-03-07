package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.base.IMapper
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.moviesdata.local.model.MovieEntity

class MovieEntityToMovieMapper(
    private val page: Int? = null,
    private val isBookmark: Boolean
) : IMapper<MovieEntity, Movie> {
    override fun mapFromEntity(entity: MovieEntity): Movie {
        return Movie(
            id = entity.id,
            name = entity.name,
            description = entity.shortDescription,
            poster = entity.poster,
            age = entity.age,
            genres = entity.genres,
            collections = entity.collections,
            isBookmark = isBookmark
        )
    }

    override fun mapToEntity(domain: Movie): MovieEntity {
        return MovieEntity(
            id = domain.id,
            name = domain.name,
            shortDescription = domain.description,
            poster = domain.poster,
            age = domain.age,
            genres = domain.genres,
            page = page ?: domain.page,
            collections = domain.collections
        )
    }
}