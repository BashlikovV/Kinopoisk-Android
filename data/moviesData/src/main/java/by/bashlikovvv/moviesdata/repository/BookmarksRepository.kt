package by.bashlikovvv.moviesdata.repository

import android.net.ConnectivityManager
import androidx.paging.PagingData
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IBookmarksRepository
import by.bashlikovvv.core.ext.isConnected
import by.bashlikovvv.moviesdata.local.dao.BookmarksDao
import by.bashlikovvv.moviesdata.mapper.BookmarkAndMovieTupleMapper
import kotlinx.coroutines.flow.Flow

class BookmarksRepository(
    private val bookmarksDao: BookmarksDao,
    private val connectivityManager: ConnectivityManager?
) : IBookmarksRepository {
    override suspend fun addMovieToBookmarks(movie: Movie) {
        if (bookmarksDao.isBookmark(movie.id) == 0) {
            bookmarksDao
                .addBookmark(
                    BookmarkAndMovieTupleMapper()
                        .mapToEntity(movie)
                        .bookmark
                )
        }
    }

    override suspend fun removeMovieFromBookmarks(movie: Movie): Boolean {
        val bookmarkEntity = bookmarksDao.getBookmarkByMovieId(movie.id)

        return if (bookmarkEntity == null) {
            false
        } else {
            bookmarksDao.removeBookmark(bookmarkEntity)

            true
        }
    }

    override suspend fun getBookmarks(): List<Movie> {
        val mapper = BookmarkAndMovieTupleMapper()

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