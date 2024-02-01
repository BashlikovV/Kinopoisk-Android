package by.bashlikovvv.moviesdata.repository

import android.net.ConnectivityManager
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IBookmarksRepository
import by.bashlikovvv.core.ext.isConnected
import by.bashlikovvv.moviesdata.local.dao.BookmarksDao
import by.bashlikovvv.moviesdata.local.tuple.BookmarkAndMovieTuple
import by.bashlikovvv.moviesdata.mapper.BookmarkAndMovieTupleMapper
import kotlinx.coroutines.flow.Flow

class BookmarksRepository(
    private val bookmarksDao: BookmarksDao,
    private val connectivityManager: ConnectivityManager?
) : IBookmarksRepository {
    override suspend fun addMovieToBookmarks(movie: Movie) {
        bookmarksDao
            .addBookmark(
                BookmarkAndMovieTupleMapper(true)
                    .mapToEntity(movie)
                    .bookmark
            )
    }

    override suspend fun removeMovieFromBookmarks(movie: Movie) {
        bookmarksDao
            .removeBookmark(
                BookmarkAndMovieTupleMapper(false)
                    .mapToEntity(movie)
                    .bookmark
            )
    }

    override suspend fun getBookmarks(): List<Movie> {
        val mapper = BookmarkAndMovieTupleMapper(true)

        return bookmarksDao.getBookmarks().map { tuple ->
            mapper.mapFromEntity(tuple)
        }
    }

    override fun getPagedBookmarks(): Flow<PagingData<Movie>> {
        return if (connectivityManager.isConnected()) {
            TODO()
        } else {
            TODO()
        }
    }

    override suspend fun isBookmark(movieId: Long): Boolean {
        return bookmarksDao.isBookmark(movieId) > 0
    }

}