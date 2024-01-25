package by.bashlikovvv.moviesdata.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.bashlikovvv.moviesdata.local.converters.IntListTypeConverter
import by.bashlikovvv.moviesdata.local.converters.StringListTypeConverter
import by.bashlikovvv.moviesdata.local.dao.MoviesDao
import by.bashlikovvv.moviesdata.local.model.MovieDetailsEntity
import by.bashlikovvv.moviesdata.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailsEntity::class],
    version = 1,
)
@TypeConverters(value = [IntListTypeConverter::class, StringListTypeConverter::class])
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
}