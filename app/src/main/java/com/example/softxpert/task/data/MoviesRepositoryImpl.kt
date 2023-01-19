package com.example.softxpert.task.data

import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.data.models.MoviesResponse
import com.example.softxpert.task.data.remote.RemoteMoviesDataSource
import com.example.softxpert.task.domain.MoviesRepository

class MoviesRepositoryImpl(private val remoteMoviesDataSource: RemoteMoviesDataSource) :
    MoviesRepository {
    override suspend fun getMovies(genresId: String?): Resource<MoviesResponse> {
        return safeApiCall { remoteMoviesDataSource.getMovies(genresId) }
    }

    override suspend fun getGenres(): Resource<GenresResponse> {
        return safeApiCall { remoteMoviesDataSource.getGenres() }
    }
}
