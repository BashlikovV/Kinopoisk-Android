package by.bashlikovvv.moviesdata.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import by.bashlikovvv.moviesdata.local.model.BookmarkEntity
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.BookmarksTable
import by.bashlikovvv.moviesdata.local.tuple.BookmarkAndMovieTuple
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesTable
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesDetailsTable

@Dao
interface BookmarksDao {

    @Insert(entity = BookmarkEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmarkEntity: BookmarkEntity)

    @Delete(entity = BookmarkEntity::class)
    suspend fun removeBookmark(bookmarkEntity: BookmarkEntity)

    @Transaction
    @Query(
        "SELECT * " +
        "FROM ${BookmarksTable.TABLE_NAME} " +
        "JOIN ${MoviesTable.TABLE_NAME} " +
        "ON ${BookmarksTable.KEY_MOVIE_ID} = ${MoviesTable.COLUMN_ID}"
    )
    suspend fun getBookmarks(): List<BookmarkAndMovieTuple>

    @Transaction
    @Query(
        "SELECT * " +
        "FROM ${BookmarksTable.TABLE_NAME} " +
        "JOIN ${MoviesTable.TABLE_NAME} " +
        "ON ${BookmarksTable.KEY_MOVIE_ID} = ${MoviesTable.COLUMN_ID}"
    )
    fun getPagedBookmarksOnline(): PagingSource<Int, BookmarkAndMovieTuple>

    @Transaction
    @Query(
        "SELECT * " +
        "FROM ${BookmarksTable.TABLE_NAME} " +
        "JOIN ${MoviesTable.TABLE_NAME} " +
        "ON ${BookmarksTable.KEY_MOVIE_ID} = ${MoviesTable.COLUMN_ID} " +
        "AND ${MoviesTable.COLUMN_ID} IN (" +
            "SELECT ${MoviesDetailsTable.COLUMN_ID} " +
            "FROM ${MoviesDetailsTable.TABLE_NAME}" +
        ")"
    )
    fun getPagedBookmarksOffline(): PagingSource<Int, BookmarkAndMovieTuple>

}