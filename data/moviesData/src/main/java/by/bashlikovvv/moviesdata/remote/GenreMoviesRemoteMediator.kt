package by.bashlikovvv.moviesdata.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import by.bashlikovvv.core.Constants
import by.bashlikovvv.moviesdata.local.dao.MoviesDao
import by.bashlikovvv.moviesdata.local.model.MovieEntity
import by.bashlikovvv.moviesdata.mapper.MovieEntityToMovieMapper
import by.bashlikovvv.moviesdata.mapper.MoviesDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GenreMoviesRemoteMediator(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
    private val genre: String
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val offset = getPageIndex(loadType, state)
            ?: return MediatorResult.Success(endOfPaginationReached = true)

        return try {
            val response = moviesApi.getMoviesByGenre(
                page = offset,
                limit = Constants.PAGE_SIZE,
                genre = genre
            )
            if (response.isSuccessful) {
                val movies = MoviesDtoMapper().mapFromEntity(
                    response.body() ?: return MediatorResult.Error(HttpException(response))
                )

                val mapper = MovieEntityToMovieMapper(offset, false)
                withContext(Dispatchers.Default) {
                    moviesDao.insertMovies(movies.map { mapper.mapToEntity(it) })
                }
                MediatorResult.Success(endOfPaginationReached = movies.size < Constants.PAGE_SIZE)
            } else {
                MediatorResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): Int? = when (loadType) {
        LoadType.REFRESH -> 1
        LoadType.APPEND -> (state.pages.maxOfOrNull { pagingSource ->
            pagingSource.data.maxOfOrNull { it.page } ?: 0
        } ?: 0) + 1
        LoadType.PREPEND -> null
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

}