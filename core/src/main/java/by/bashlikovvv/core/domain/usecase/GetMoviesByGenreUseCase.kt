package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IMoviesRepository

class GetMoviesByGenreUseCase(private val moviesRepository: IMoviesRepository) {

    suspend fun execute(genre: String): List<Movie> {
        return moviesRepository.getMoviesByGenre(genre)
    }

}