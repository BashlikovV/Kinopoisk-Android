package by.bashlikovvv.core.domain.usecase

import androidx.paging.PagingData
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class GetPagedMoviesByGenreUseCase(private val moviesRepository: IMoviesRepository) {

    fun execute(genre: String): Flow<PagingData<Movie>> {
        return moviesRepository.getPagedMoviesByGenre(genre)
    }

}