package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IBookmarksRepository

class GetBookmarksUseCase(private val bookmarksRepository: IBookmarksRepository) {

    suspend fun execute(): List<Movie> {
        return bookmarksRepository.getBookmarks()
    }

}