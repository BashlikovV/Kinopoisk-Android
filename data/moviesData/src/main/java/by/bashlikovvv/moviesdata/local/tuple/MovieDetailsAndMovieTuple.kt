package by.bashlikovvv.moviesdata.local.tuple

import androidx.room.Embedded
import androidx.room.Relation
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesDetailsTable
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract.MoviesTable
import by.bashlikovvv.moviesdata.local.model.MovieDetailsEntity
import by.bashlikovvv.moviesdata.local.model.MovieEntity

data class MovieDetailsAndMovieTuple(
    @Embedded val movieDetailsEntity: MovieDetailsEntity,
    @Relation(
        parentColumn = MoviesDetailsTable.KEY_MOVIE_ID,
        entityColumn = MoviesTable.COLUMN_ID,
        entity = MovieEntity::class
    ) val movieEntity: MovieEntity
)