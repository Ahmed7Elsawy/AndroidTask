package com.example.softxpert.task.di

import com.example.softxpert.task.data.MoviesRepositoryImpl
import com.example.softxpert.task.data.remote.ApiService
import com.example.softxpert.task.data.remote.RemoteMoviesDataSource
import com.example.softxpert.task.data.remote.RemoteMoviesDataSourceImpl
import com.example.softxpert.task.domain.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRemoteMoviesDataSource(apiService: ApiService): RemoteMoviesDataSource {
        return RemoteMoviesDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideMoviesRepository(remoteMoviesDataSource: RemoteMoviesDataSource): MoviesRepository {
        return MoviesRepositoryImpl(remoteMoviesDataSource)
    }

}
