package by.bashlikovvv.core.domain.repository

import by.bashlikovvv.core.domain.model.MovieDetails

interface IMoviesDetailsRepository {

    suspend fun getMovieDetailsById(id: Long): MovieDetails?

}