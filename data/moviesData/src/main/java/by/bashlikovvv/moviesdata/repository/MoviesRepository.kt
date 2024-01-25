package by.bashlikovvv.moviesdata.repository

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import by.bashlikovvv.core.di.PagerOffline
import by.bashlikovvv.core.di.PagerOnline
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.repository.IMoviesRepository
import by.bashlikovvv.moviesdata.local.dao.MoviesDao
import by.bashlikovvv.moviesdata.local.model.MovieEntity
import by.bashlikovvv.moviesdata.mapper.MovieDtoToMovieMapper
import by.bashlikovvv.moviesdata.mapper.MovieEntityToMovieMapper
import by.bashlikovvv.moviesdata.remote.MoviesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepository(
    private val cm: ConnectivityManager?,
    @PagerOnline private val pagerOnline: Pager<Int, MovieEntity>,
    @PagerOffline private val pagerOffline: Pager<Int, MovieEntity>,
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao
) : IMoviesRepository{

    override suspend fun getMovieById(id: Int): Movie {
        return if (isConnected()) {
            MovieDtoToMovieMapper()
                .mapFromEntity(moviesApi.getMovieById(id).body() ?: TODO())
        } else {
            MovieEntityToMovieMapper()
                .mapFromEntity(moviesDao.getMovieById(id))
        }
    }

    override fun getPagedMovies(): Flow<PagingData<Movie>> {
        return if (isConnected()) {
            getMoviesOnline()
        } else {
            getMoviesOffline()
        }
    }

    override suspend fun getMoviesByName(name: String): List<Movie> {
        val moviesDto = moviesApi.getMoviesByName(query = name)
        TODO("Not yet implemented")
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

    private fun isConnected(): Boolean {
        var isConnected = false
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                isConnected = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return isConnected
    }

}