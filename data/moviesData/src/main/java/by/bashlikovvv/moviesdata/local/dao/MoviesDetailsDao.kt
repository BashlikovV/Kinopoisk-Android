package by.bashlikovvv.moviesdata.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import by.bashlikovvv.core.domain.model.MovieDetails
import by.bashlikovvv.moviesdata.local.model.MovieDetailsEntity
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesDetailsTable
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesTable
import by.bashlikovvv.moviesdata.local.tuple.MovieDetailsAndMovieTuple


@Dao
interface MoviesDetailsDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(movieDetailsEntity: MovieDetailsEntity)

    @Transaction
    @Query(
        "SELECT * " +
        "FROM ${MoviesDetailsTable.TABLE_NAME} " +
        "WHERE ${MoviesDetailsTable.KEY_MOVIE_ID} = :movieId"
    )
    fun getMovieDetailsByMovieId(movieId: Long): MovieDetailsEntity?

    @Transaction
    @Query(
        "SELECT * " +
        "FROM ${MoviesDetailsTable.TABLE_NAME} " +
        "WHERE ${MoviesDetailsTable.COLUMN_ID} = :id"
    )
    fun getMovieDetailsById(id: Long): MovieDetailsEntity?

    @Transaction
    @Query(
        "SELECT * " +
        "FROM ${MoviesDetailsTable.TABLE_NAME} " +
        "JOIN ${MoviesTable.TABLE_NAME} " +
        "ON ${MoviesDetailsTable.KEY_MOVIE_ID} = ${MoviesTable.COLUMN_ID} " +
        "AND ${MoviesTable.COLUMN_ID} = :id"
    )
    fun getMovieDetailsAndMovieById(id: Long): MovieDetailsAndMovieTuple?

}