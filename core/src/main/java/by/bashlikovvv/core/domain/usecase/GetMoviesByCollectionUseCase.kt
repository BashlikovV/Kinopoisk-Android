package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IMoviesRepository

class GetMoviesByCollectionUseCase(private val moviesRepository: IMoviesRepository) {

    suspend fun execute(collection: String): List<Movie> {
        return moviesRepository.getMoviesByCollection(collection)
    }

}