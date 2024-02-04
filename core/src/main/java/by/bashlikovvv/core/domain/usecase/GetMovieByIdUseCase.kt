package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.model.EmptyBodyException
import by.bashlikovvv.core.domain.model.ResultCommon
import by.bashlikovvv.core.domain.repository.IMoviesRepository

class GetMovieByIdUseCase(private val moviesRepository: IMoviesRepository) {

    suspend fun execute(id: Long): ResultCommon {
        return try {
            ResultCommon.Success(moviesRepository.getMovieById(id))
        } catch (e: EmptyBodyException) {
            ResultCommon.Error(e.cause)
        }
    }

}