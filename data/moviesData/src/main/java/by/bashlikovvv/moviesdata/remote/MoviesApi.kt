package by.bashlikovvv.moviesdata.remote

import by.bashlikovvv.moviesdata.remote.response.MovieDto
import by.bashlikovvv.moviesdata.remote.response.MoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int
    ): Response<MovieDto>


    @GET("movie")
    suspend fun getMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<MoviesDto>

    @GET("movie/search")
    suspend fun getMoviesByName(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("query") query: String
    ): Response<List<MovieDto>>



}