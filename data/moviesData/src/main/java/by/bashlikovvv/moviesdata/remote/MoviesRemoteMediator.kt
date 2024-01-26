package by.bashlikovvv.moviesdata.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import by.bashlikovvv.core.Constants.PAGES_COUNT
import by.bashlikovvv.core.Constants.PAGE_SIZE
import by.bashlikovvv.moviesdata.local.dao.MoviesDao
import by.bashlikovvv.moviesdata.local.model.MovieEntity
import by.bashlikovvv.moviesdata.mapper.MovieEntityToMovieMapper
import by.bashlikovvv.moviesdata.mapper.MoviesDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao
) : RemoteMediator<Int, MovieEntity>() {

    private var pageCount = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val offset = getPageIndex(loadType)
            ?: return MediatorResult.Success(endOfPaginationReached = true)

        return try {
            val response = moviesApi.getMovies(offset, PAGE_SIZE)
            if (response.isSuccessful) {
                val movies = MoviesDtoMapper().mapFromEntity(
                    response.body() ?: return MediatorResult.Error(HttpException(response))
                )

                val mapper = MovieEntityToMovieMapper()
                withContext(Dispatchers.Default) {
                    moviesDao.insertMovies(movies.map { mapper.mapToEntity(it) })
                }
                MediatorResult.Success(endOfPaginationReached = movies.size < PAGES_COUNT)
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
        loadType: LoadType
    ): Int? = when (loadType) {
        LoadType.REFRESH -> 0
        LoadType.APPEND -> {
            pageCount += PAGE_SIZE

            pageCount
        }
        LoadType.PREPEND -> null
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

}