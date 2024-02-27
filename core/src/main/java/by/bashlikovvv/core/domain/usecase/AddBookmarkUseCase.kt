package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IBookmarksRepository
import by.bashlikovvv.core.domain.repository.IMoviesDetailsRepository

class AddBookmarkUseCase(
    private val bookmarksRepository: IBookmarksRepository,
    private val moviesDetailsRepository: IMoviesDetailsRepository
) {

    suspend fun execute(movie: Movie) {
        bookmarksRepository.addMovieToBookmarks(movie)
        moviesDetailsRepository.addMovieToDetails(movie)
    }

}