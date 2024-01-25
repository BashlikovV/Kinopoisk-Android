package by.bashlikovvv.moviesdata.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.Index
import androidx.room.PrimaryKey
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesDetailsTable
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesTable

@Entity(
    tableName = MoviesDetailsTable.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = [MoviesTable.COLUMN_ID],
            childColumns = [MoviesDetailsTable.KEY_MOVIE_ID],
            onDelete = CASCADE,
            onUpdate = NO_ACTION,
            deferred = true
        )
    ],
    indices = [
        Index(
            value = [MoviesDetailsTable.KEY_MOVIE_ID],
            unique = false
        )
    ]
)
data class MovieDetailsEntity(
    @[
        PrimaryKey(autoGenerate = true)
        ColumnInfo(name = MoviesDetailsTable.COLUMN_ID)
    ] val id: Int,
    @ColumnInfo(name = MoviesDetailsTable.COLUMN_PERSONS) val persons: List<Int>,
    @ColumnInfo(name = MoviesDetailsTable.COLUMN_DESCRIPTION) val description: String,
    @ColumnInfo(name = MoviesDetailsTable.COLUMN_SIMILAR_MOVIES) val similarMovies: List<Int>,
    @ColumnInfo(name = MoviesDetailsTable.COLUMN_WATCH_ABILITY) val watchAbility : List<String>,
    @ColumnInfo(name = MoviesDetailsTable.COLUMN_TRAILERS) val trailers: List<String>,
    @ColumnInfo(name = MoviesDetailsTable.KEY_MOVIE_ID) val keyMovieId: Int
)
