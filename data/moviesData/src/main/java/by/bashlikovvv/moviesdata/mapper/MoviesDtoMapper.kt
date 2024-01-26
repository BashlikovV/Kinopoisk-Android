package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.domain.model.IMapper
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.moviesdata.remote.response.MoviesDto

class MoviesDtoMapper : IMapper<MoviesDto, List<Movie>> {
    override fun mapFromEntity(entity: MoviesDto): List<Movie> {
        return entity.docs?.map { docsItem ->
            Movie(
                id = docsItem.id,
                name = docsItem.name,
                description = docsItem.shortDescription,
                poster = docsItem.poster.previewUrl,
                age = docsItem.ageRating,
                genres = (docsItem.genres ?: listOf()).map { it.name }
            )
        } ?: listOf()
    }

    override fun mapToEntity(domain: List<Movie>): MoviesDto {
        throw IllegalStateException()
    }
}