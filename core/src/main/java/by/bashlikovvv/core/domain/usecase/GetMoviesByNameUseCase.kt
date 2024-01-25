package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IMoviesRepository

class GetMoviesByNameUseCase(private val moviesRepository: IMoviesRepository) {

    suspend fun execute(name: String): List<Movie> {
        return moviesRepository.getMoviesByName(name)
    }

}