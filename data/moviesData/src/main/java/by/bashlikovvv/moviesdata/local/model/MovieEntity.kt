package by.bashlikovvv.moviesdata.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesTable

@Entity(tableName = MoviesTable.TABLE_NAME)
data class MovieEntity(
    @[
        PrimaryKey(autoGenerate = false)
        ColumnInfo(name = MoviesTable.COLUMN_ID)
    ] val id: Long,
    @ColumnInfo(name = MoviesTable.COLUMN_NAME) val name: String,
    @ColumnInfo(name = MoviesTable.COLUMN_SHORT_DESCRIPTION) val shortDescription: String,
    @ColumnInfo(name = MoviesTable.COLUMN_POSTER) val poster: String,
    @ColumnInfo(name = MoviesTable.COLUMN_AGE) val age: Int,
    @ColumnInfo(name = MoviesTable.COLUMN_GENRES) val genres: List<String>,
    @ColumnInfo(name = MoviesTable.COLUMN_PAGE) val page: Int,
    @ColumnInfo(name = MoviesTable.COLUMN_COLLECTIONS) val collections: List<String>
)