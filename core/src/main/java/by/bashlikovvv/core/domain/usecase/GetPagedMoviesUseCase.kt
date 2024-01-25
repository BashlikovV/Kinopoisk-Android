package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.repository.IMoviesRepository

class GetPagedMoviesUseCase(private val moviesRepository: IMoviesRepository) {

    fun execute() = moviesRepository.getPagedMovies()

}