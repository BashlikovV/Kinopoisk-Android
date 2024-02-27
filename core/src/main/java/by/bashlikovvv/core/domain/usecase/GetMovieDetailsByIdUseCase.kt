package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.model.ResultCommon
import by.bashlikovvv.core.domain.repository.IMoviesDetailsRepository

class GetMovieDetailsByIdUseCase(private val movieDetailsRepository: IMoviesDetailsRepository) {

    suspend fun execute(id: Long): ResultCommon {
        val movieDetails = movieDetailsRepository.getMovieDetailsById(id)

        return if (movieDetails == null) {
            ResultCommon.Error(NullPointerException())
        } else {
            ResultCommon.Success(movieDetails)
        }
    }

}