package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IBookmarksRepository

class RemoveBookmarkUseCase(private val bookmarksRepository: IBookmarksRepository) {

    suspend fun execute(movie: Movie): Boolean {
        return bookmarksRepository.removeMovieFromBookmarks(movie)
    }

}