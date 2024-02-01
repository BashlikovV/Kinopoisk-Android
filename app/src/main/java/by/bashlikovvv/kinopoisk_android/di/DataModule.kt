package by.bashlikovvv.kinopoisk_android.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import by.bashlikovvv.core.Constants
import by.bashlikovvv.core.di.AppScope
import by.bashlikovvv.core.di.ApplicationQualifier
import by.bashlikovvv.core.di.PagerOffline
import by.bashlikovvv.core.di.PagerOnline
import by.bashlikovvv.core.domain.model.OkHttpConfig
import by.bashlikovvv.core.domain.repository.IBookmarksRepository
import by.bashlikovvv.core.domain.repository.IMoviesRepository
import by.bashlikovvv.moviesdata.local.MoviesDatabase
import by.bashlikovvv.moviesdata.local.contract.MoviesRoomContract
import by.bashlikovvv.moviesdata.local.dao.BookmarksDao
import by.bashlikovvv.moviesdata.local.dao.MoviesDao
import by.bashlikovvv.moviesdata.local.model.MovieEntity
import by.bashlikovvv.moviesdata.remote.MoviesApi
import by.bashlikovvv.moviesdata.remote.MoviesRemoteMediator
import by.bashlikovvv.moviesdata.repository.BookmarksRepository
import by.bashlikovvv.moviesdata.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@Module
class DataModule {

    @[Provides AppScope]
    fun provideOkHttpConfig() = OkHttpConfig()

    @[Provides Inject AppScope]
    fun provideOkHttpClient(okHttpConfig: OkHttpConfig): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(okHttpConfig.apiKeyName, okHttpConfig.apiKey)
                    .build()

                chain.proceed(newRequest)
            }
            .build()
    }

    @[Provides AppScope]
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @[Provides Inject AppScope]
    fun provideRetrofit(
        okHttpConfig: OkHttpConfig,
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(okHttpConfig.baseUrl)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()

    @[Provides Inject AppScope]
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @[Provides AppScope]
    fun provideMoviesDatabase(@ApplicationQualifier context: Application): MoviesDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MoviesDatabase::class.java,
            name = MoviesRoomContract.DATABASE_NAME
        ).build()
    }

    @[Provides Inject AppScope]
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao {
        return moviesDatabase.moviesDao
    }

    @[Provides Inject AppScope]
    fun provideBookmarksDao(moviesDatabase: MoviesDatabase): BookmarksDao {
        return moviesDatabase.bookmarksDao
    }

    @OptIn(ExperimentalPagingApi::class)
    @[Provides Inject PagerOnline AppScope]
    fun providePagerOnline(
        moviesDao: MoviesDao,
        moviesApi: MoviesApi
    ): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                initialLoadSize = Constants.PAGE_SIZE,
                maxSize = Constants.PAGES_COUNT,
                prefetchDistance = Constants.PAGE_SIZE
            ),
            remoteMediator = MoviesRemoteMediator(
                moviesApi = moviesApi,
                moviesDao = moviesDao
            )
        ) {
            moviesDao.getMoviesOnline()
        }
    }

    @[Provides Inject PagerOffline AppScope]
    fun providePagerOffline(
        moviesDao: MoviesDao
    ): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                initialLoadSize = Constants.PAGE_SIZE,
                maxSize = Constants.PAGES_COUNT
            )
        ) {
            moviesDao.getMoviesOffline()
        }
    }

    @[Provides Inject AppScope]
    fun provideMoviesRepository(
        @ApplicationQualifier context: Application,
        @[PagerOnline] pagerOnline: Pager<Int, MovieEntity>,
        @[PagerOffline] pagerOffline: Pager<Int, MovieEntity>,
        moviesApi: MoviesApi,
        moviesDao: MoviesDao
    ): IMoviesRepository {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        return MoviesRepository(
            cm = connectivityManager,
            pagerOnline = pagerOnline,
            pagerOffline = pagerOffline,
            moviesApi = moviesApi,
            moviesDao = moviesDao
        )
    }

    @[Provides Inject AppScope]
    fun provideBookmarksRepository(
        @ApplicationQualifier context: Application,
        bookmarksDao: BookmarksDao
    ): IBookmarksRepository {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        return BookmarksRepository(
            bookmarksDao = bookmarksDao,
            connectivityManager = connectivityManager
        )
    }

}