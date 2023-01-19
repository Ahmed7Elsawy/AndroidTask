package com.example.softxpert.task.domain

import com.example.softxpert.task.data.Resource
import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.data.models.MoviesResponse

interface MoviesRepository {
    suspend fun getMovies(genresId: String?): Resource<MoviesResponse>
    suspend fun getGenres(): Resource<GenresResponse>
}
