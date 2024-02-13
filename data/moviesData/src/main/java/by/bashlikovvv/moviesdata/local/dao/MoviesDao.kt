package by.bashlikovvv.moviesdata.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.bashlikovvv.moviesdata.local.model.MovieEntity
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesTable
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesDetailsTable

@Dao
interface MoviesDao {

    @Query(
        "SELECT * " +
        "FROM ${MoviesTable.TABLE_NAME} " +
        "WHERE ${MoviesTable.COLUMN_ID} = :id"
    )
    suspend fun getMovieById(id: Long): MovieEntity

    @Query(
        "SELECT * " +
        "FROM ${MoviesTable.TABLE_NAME}"
    )
    fun getMoviesOnline(): PagingSource<Int, MovieEntity>

    @Query(
        "SELECT * " +
        "FROM ${MoviesTable.TABLE_NAME} " +
        "WHERE ${MoviesTable.COLUMN_ID} " +
        "IN (" +
            "SELECT ${MoviesDetailsTable.COLUMN_ID} " +
            "FROM ${MoviesDetailsTable.TABLE_NAME}" +
        ")"
    )
    fun getMoviesOffline(): PagingSource<Int, MovieEntity>

    @Insert(entity = MovieEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

//    WHERE ${MoviesTable.COLUMN_GENRES} LIKE %:genre%
    @Query(
        "SELECT * " +
        "FROM ${MoviesTable.TABLE_NAME} " +
        "WHERE instr(${MoviesTable.COLUMN_GENRES}, :genre) > 0"
    )
    fun getMoviesByGenreOnline(genre: String): List<MovieEntity>

    @Query(
        "SELECT * " +
        "FROM ${MoviesTable.TABLE_NAME} " +
        "WHERE ${MoviesTable.COLUMN_ID} " +
        "IN (" +
            "SELECT ${MoviesDetailsTable.COLUMN_ID} " +
            "FROM ${MoviesDetailsTable.TABLE_NAME}" +
        ") " +
        "AND instr(${MoviesTable.COLUMN_GENRES}, :genre) > 0"
    )
    fun getMoviesByGenreOffline(genre: String): List<MovieEntity>

    @Query(
        "SELECT * " +
        "FROM ${MoviesTable.TABLE_NAME} " +
        "WHERE instr(${MoviesTable.COLUMN_COLLECTIONS}, :collection) > 0"
    )
    fun getMoviesByCollection(collection: String): List<MovieEntity>

    @Query(
        "SELECT * " +
        "FROM ${MoviesTable.TABLE_NAME} " +
        "WHERE instr(${MoviesTable.COLUMN_GENRES}, :genre) > 0"
    )
    fun getPagedMoviesByGenreOnline(genre: String): PagingSource<Int, MovieEntity>

    @Query(
        "SELECT * " +
        "FROM ${MoviesTable.TABLE_NAME} " +
        "WHERE ${MoviesTable.COLUMN_ID} " +
        "IN (" +
            "SELECT ${MoviesDetailsTable.COLUMN_ID} " +
            "FROM ${MoviesDetailsTable.TABLE_NAME}" +
        ") " +
        "AND instr(${MoviesTable.COLUMN_GENRES}, :genre) > 0"
    )
    fun getPagedMoviesByGenreOffline(genre: String): PagingSource<Int, MovieEntity>

    @Query(
        "SELECT * " +
        "FROM ${MoviesTable.TABLE_NAME} " +
        "WHERE instr(${MoviesTable.COLUMN_COLLECTIONS}, :collection) > 0"
    )
    fun getPagedMoviesByCollection(collection: String): PagingSource<Int, MovieEntity>

}