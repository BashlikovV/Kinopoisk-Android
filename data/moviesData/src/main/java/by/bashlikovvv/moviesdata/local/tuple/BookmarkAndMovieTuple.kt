package by.bashlikovvv.moviesdata.local.tuple

import androidx.room.Embedded
import androidx.room.Relation
import by.bashlikovvv.moviesdata.local.model.BookmarkEntity
import by.bashlikovvv.moviesdata.local.model.MovieEntity
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesTable
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.BookmarksTable

data class BookmarkAndMovieTuple(
    @Embedded val bookmark: BookmarkEntity,
    @Relation(
        parentColumn = BookmarksTable.KEY_MOVIE_ID,
        entityColumn = MoviesTable.COLUMN_ID,
        entity = MovieEntity::class
    ) val movie: MovieEntity
)