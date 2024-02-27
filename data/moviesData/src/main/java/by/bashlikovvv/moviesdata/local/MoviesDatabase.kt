package by.bashlikovvv.moviesdata.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.bashlikovvv.moviesdata.local.converters.IntListTypeConverter
import by.bashlikovvv.moviesdata.local.converters.LongListTypeConverter
import by.bashlikovvv.moviesdata.local.converters.StringListTypeConverter
import by.bashlikovvv.moviesdata.local.dao.BookmarksDao
import by.bashlikovvv.moviesdata.local.dao.MoviesDao
import by.bashlikovvv.moviesdata.local.dao.MoviesDetailsDao
import by.bashlikovvv.moviesdata.local.model.BookmarkEntity
import by.bashlikovvv.moviesdata.local.model.MovieDetailsEntity
import by.bashlikovvv.moviesdata.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailsEntity::class, BookmarkEntity::class],
    version = 1,
)
@TypeConverters(
    value = [IntListTypeConverter::class, StringListTypeConverter::class, LongListTypeConverter::class]
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao

    abstract val bookmarksDao: BookmarksDao

    abstract val moviesDetailsDao: MoviesDetailsDao

}