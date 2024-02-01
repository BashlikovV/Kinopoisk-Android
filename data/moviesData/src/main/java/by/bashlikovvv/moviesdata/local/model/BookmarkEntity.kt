package by.bashlikovvv.moviesdata.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.BookmarksTable
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesTable

@Entity(
    tableName = BookmarksTable.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = [MoviesTable.COLUMN_ID],
            childColumns = [BookmarksTable.KEY_MOVIE_ID],
            onDelete = CASCADE,
            onUpdate = CASCADE,
            deferred = true
        )
    ]
)
data class BookmarkEntity(
    @[
        PrimaryKey(autoGenerate = true)
        ColumnInfo(name = BookmarksTable.COLUMN_ID)
    ] val id: Int,
    @ColumnInfo(name = BookmarksTable.KEY_MOVIE_ID, index = true) val keyMovieId: Int
)