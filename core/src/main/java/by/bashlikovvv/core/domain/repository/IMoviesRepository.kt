package by.bashlikovvv.core.domain.repository

import androidx.paging.PagingData
import by.bashlikovvv.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

    suspend fun getMovieById(id: Long): Movie

    fun getPagedMovies(): Flow<PagingData<Movie>>

    suspend fun getMoviesByName(name: String): List<Movie>

    suspend fun getMoviesByGenre(genre: String): List<Movie>

    fun getPagedMoviesByGenre(genre: String): Flow<PagingData<Movie>>

    fun getPagedMoviesByGenreAndName(genre: String, name: String): Flow<PagingData<Movie>>

    suspend fun getMoviesByCollection(collection: String): List<Movie>

}