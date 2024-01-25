package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IMoviesRepository

class GetMovieByIdUseCase(private val moviesRepository: IMoviesRepository) {

    suspend fun execute(id: Int): Movie {
        return moviesRepository.getMovieById(id)
    }

}