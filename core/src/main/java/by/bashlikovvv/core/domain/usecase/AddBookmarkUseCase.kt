package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IBookmarksRepository

class AddBookmarkUseCase(private val bookmarksRepository: IBookmarksRepository) {

    suspend fun execute(movie: Movie) {
        bookmarksRepository.addMovieToBookmarks(movie)
    }

}