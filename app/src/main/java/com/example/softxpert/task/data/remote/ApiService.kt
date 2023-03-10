package com.example.softxpert.task.data.remote

import com.example.softxpert.task.BuildConfig
import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.data.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("with_genres") withGenres: Int? = null,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
    ): Response<MoviesResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US"
    ): Response<GenresResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): Response<MoviesResponse>

}
