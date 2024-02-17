package by.bashlikovvv.core.domain.usecase

import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IBookmarksRepository

class GetBookmarksByNameUseCase(private val bookmarksRepository: IBookmarksRepository) {

    suspend fun execute(name: String): List<Movie> {
        return bookmarksRepository.getBookmarksByName(name)
    }

}