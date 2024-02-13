package by.bashlikovvv.moviesdata.repository

import android.net.ConnectivityManager
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import by.bashlikovvv.core.di.PagerOffline
import by.bashlikovvv.core.di.PagerOnline
import by.bashlikovvv.core.domain.model.EmptyBodyException
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IMoviesRepository
import by.bashlikovvv.moviesdata.local.dao.MoviesDao
import by.bashlikovvv.moviesdata.local.model.MovieEntity
import by.bashlikovvv.moviesdata.mapper.MovieDtoToMovieMapper
import by.bashlikovvv.moviesdata.mapper.MovieEntityToMovieMapper
import by.bashlikovvv.moviesdata.mapper.MoviesDtoMapper
import by.bashlikovvv.moviesdata.remote.MoviesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import by.bashlikovvv.core.ext.isConnected
import by.bashlikovvv.moviesdata.local.dao.BookmarksDao
import kotlinx.coroutines.flow.transform

class MoviesRepository(
    private val cm: ConnectivityManager?,
    @PagerOnline private val pagerOnline: Pager<Int, MovieEntity>,
    @PagerOffline private val pagerOffline: Pager<Int, MovieEntity>,
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
    private val bookmarksDao: BookmarksDao
) : IMoviesRepository {

    override suspend fun getMovieById(id: Long): Movie {
        return if (cm.isConnected()) {
            MovieDtoToMovieMapper(bookmarksDao.isBookmark(id) > 0)
                .mapFromEntity(
                    moviesApi.getMovieById(id).body() ?: throw EmptyBodyException()
                )
        } else {
            MovieEntityToMovieMapper(isBookmark = bookmarksDao.isBookmark(id) > 0)
                .mapFromEntity(moviesDao.getMovieById(id))
        }
    }

    override fun getPagedMovies(): Flow<PagingData<Movie>> {
        return if (cm.isConnected()) {
            getMoviesOnline()
        } else {
            getMoviesOffline()
        }
    }

    override suspend fun getMoviesByName(name: String): List<Movie> {
        val moviesDto = moviesApi.getMoviesByName(query = name).body()
        moviesDto ?: throw EmptyBodyException()

        return MoviesDtoMapper().mapFromEntity(moviesDto)
    }

    override suspend fun getMoviesByGenre(genre: String): List<Movie> {
        return if (cm.isConnected()) {
            val moviesDto = moviesApi.getMoviesByGenre(genre = genre).body() ?: return listOf()

            MoviesDtoMapper().mapFromEntity(moviesDto)
        } else {
            val movieEntities = moviesDao.getMoviesByGenreOffline(genre)

            movieEntities.map {
                val mapper = MovieEntityToMovieMapper(
                    isBookmark = bookmarksDao.isBookmark(it.id) > 0
                )

                mapper.mapFromEntity(it)
            }
        }
    }

    override fun getPagedMoviesByGenre(genre: String): Flow<PagingData<Movie>> {
        return if (cm.isConnected()) {
            val pager = getMoviesPagerByGenreOnline(genre)

            val flow = pager.flow.transform<PagingData<MovieEntity>, PagingData<Movie>> { pagingData ->
                pagingData.map {
                    MovieEntityToMovieMapper(
                        isBookmark = bookmarksDao.isBookmark(it.id) > 0
                    ).mapFromEntity(it)
                }
            }

            flow
        } else {
            val pager = getMoviesPagerByGenreOffline(genre)

            pager.flow.transform { pagingData ->
                pagingData.map {
                    MovieEntityToMovieMapper(
                        isBookmark = bookmarksDao.isBookmark(it.id) > 0
                    ).mapFromEntity(it)
                }
            }
        }
    }

    override suspend fun getMoviesByCollection(collection: String): List<Movie> {
        return if (cm.isConnected()) {
            val moviesDto = moviesApi
                .getMoviesByCollection(lists = collection).body() ?: return listOf()

            MoviesDtoMapper().mapFromEntity(moviesDto)
        } else {
            val moviesEntity = moviesDao.getMoviesByCollection(collection)

            moviesEntity.map { entity ->
                val mapper = MovieEntityToMovieMapper(
                    isBookmark = bookmarksDao.isBookmark(entity.id) > 0
                )

                mapper.mapFromEntity(entity)
            }
        }

    }

    private fun getMoviesOnline(): Flow<PagingData<Movie>> {
        return pagerOnline.flow.map { pagingData ->
            pagingData.map { movieEntity ->
                val mapper = MovieEntityToMovieMapper(
                    isBookmark = bookmarksDao.isBookmark(movieEntity.id) > 0
                )

                mapper.mapFromEntity(movieEntity)
            }
        }
    }

    private fun getMoviesOffline(): Flow<PagingData<Movie>> {
        return pagerOffline.flow.map { pagingData ->
            pagingData.map { movieEntity ->
                val mapper = MovieEntityToMovieMapper(
                    isBookmark = bookmarksDao.isBookmark(movieEntity.id) > 0
                )

                mapper.mapFromEntity(movieEntity)
            }
        }
    }

    private fun getMoviesPagerByGenreOnline(genre: String): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = MORE_MOVIES_PAGE_SIZE,
                initialLoadSize = MORE_MOVIES_PAGE_SIZE,
                maxSize = MORE_MOVIES_PAGE_SIZE * 10
            )
        ) {
            moviesDao.getPagedMoviesByGenreOnline(genre)
        }
    }

    private fun getMoviesPagerByGenreOffline(genre: String): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = MORE_MOVIES_PAGE_SIZE,
                initialLoadSize = MORE_MOVIES_PAGE_SIZE
            )
        ) {
            moviesDao.getPagedMoviesByGenreOffline(genre)
        }
    }

    companion object {
        const val MORE_MOVIES_PAGE_SIZE = 15
    }

}