package by.bashlikovvv.core.domain.repository

import androidx.paging.PagingData
import by.bashlikovvv.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

    suspend fun getMovieById(id: Int): Movie

    fun getPagedMovies(): Flow<PagingData<Movie>>

    suspend fun getMoviesByName(name: String): List<Movie>

}