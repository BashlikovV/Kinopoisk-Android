package by.bashlikovvv.moviesdata.repository

import android.net.ConnectivityManager
import androidx.paging.Pager
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

class MoviesRepository(
    private val cm: ConnectivityManager?,
    @PagerOnline private val pagerOnline: Pager<Int, MovieEntity>,
    @PagerOffline private val pagerOffline: Pager<Int, MovieEntity>,
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao
) : IMoviesRepository {

    override suspend fun getMovieById(id: Int): Movie {
        return if (cm.isConnected()) {
            MovieDtoToMovieMapper()
                .mapFromEntity(
                    moviesApi.getMovieById(id).body() ?: throw EmptyBodyException()
                )
        } else {
            MovieEntityToMovieMapper()
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

            val mapper = MovieEntityToMovieMapper()
            movieEntities.map { mapper.mapFromEntity(it) }
        }
    }

    override fun getPagedMoviesByGenre(genre: String): Flow<PagingData<Movie>> {
        return if (cm.isConnected()) {
            TODO()
        } else {
            TODO()
        }
    }

    override suspend fun getMoviesByCollection(collection: String): List<Movie> {
        return if (cm.isConnected()) {
            val moviesDto = moviesApi
                .getMoviesByCollection(lists = collection).body() ?: return listOf()

            MoviesDtoMapper().mapFromEntity(moviesDto)
        } else {
            val moviesEntity = moviesDao.getMoviesByCollection(collection)

            val mapper = MovieEntityToMovieMapper()
            moviesEntity.map { entity ->
                mapper.mapFromEntity(entity)
            }
        }

    }

    private fun getMoviesOnline(): Flow<PagingData<Movie>> {
        val mapper = MovieEntityToMovieMapper()

        return pagerOnline.flow.map { pagingData ->
            pagingData.map { movieEntity ->
                mapper.mapFromEntity(movieEntity)
            }
        }
    }

    private fun getMoviesOffline(): Flow<PagingData<Movie>> {
        val mapper = MovieEntityToMovieMapper()

        return pagerOffline.flow.map { pagingData ->
            pagingData.map { movieEntity ->
                mapper.mapFromEntity(movieEntity)
            }
        }
    }

}