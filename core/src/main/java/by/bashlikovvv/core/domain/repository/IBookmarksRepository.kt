package by.bashlikovvv.core.domain.repository

import androidx.paging.PagingData
import by.bashlikovvv.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IBookmarksRepository {

    suspend fun addMovieToBookmarks(movie: Movie)

    suspend fun removeMovieFromBookmarks(movie: Movie): Boolean

    suspend fun getBookmarks(): List<Movie>

    suspend fun getBookmarksByName(name: String): List<Movie>

    fun getPagedBookmarks(): Flow<PagingData<Movie>>

    suspend fun isBookmark(movieId: Long): Boolean

}