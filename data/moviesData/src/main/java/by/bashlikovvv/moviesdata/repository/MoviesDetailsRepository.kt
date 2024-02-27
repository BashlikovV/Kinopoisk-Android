package by.bashlikovvv.moviesdata.repository

import android.net.ConnectivityManager
import by.bashlikovvv.core.domain.model.MovieDetails
import by.bashlikovvv.core.domain.repository.IMoviesDetailsRepository
import by.bashlikovvv.core.ext.isConnected
import by.bashlikovvv.moviesdata.local.dao.MoviesDetailsDao
import by.bashlikovvv.moviesdata.mapper.MovieDetailsEntityToMovieDetailsMapper
import by.bashlikovvv.moviesdata.mapper.MovieDtoToMovieDetailsMapper
import by.bashlikovvv.moviesdata.mapper.MovieDtoToMovieMapper
import by.bashlikovvv.moviesdata.mapper.MovieEntityToMovieMapper
import by.bashlikovvv.moviesdata.remote.MoviesApi

class MoviesDetailsRepository(
    private val connectivityManager: ConnectivityManager?,
    private val moviesApi: MoviesApi,
    private val moviesDetailsDao: MoviesDetailsDao
) : IMoviesDetailsRepository {
    override suspend fun getMovieDetailsById(id: Long): MovieDetails? {
        return if (connectivityManager.isConnected()) {
            val movieDto = moviesApi.getMovieById(id).body() ?: return null
            val movieDetails = MovieDtoToMovieDetailsMapper().mapFromEntity(movieDto)

            moviesDetailsDao.insertMovieDetails(
                MovieDetailsEntityToMovieDetailsMapper(
                    MovieDtoToMovieMapper(false).mapFromEntity(movieDto)
                ).mapToEntity(movieDetails)
            )

            movieDetails
        } else {
            val movieDetailsAndMovieTuple = moviesDetailsDao.getMovieDetailsAndMovieById(id)
            movieDetailsAndMovieTuple ?: return null

            val movie = MovieEntityToMovieMapper(
                isBookmark = false
            ).mapFromEntity(movieDetailsAndMovieTuple.movieEntity)

            return MovieDetailsEntityToMovieDetailsMapper(movie)
                .mapFromEntity(movieDetailsAndMovieTuple.movieDetailsEntity)
        }
    }
}