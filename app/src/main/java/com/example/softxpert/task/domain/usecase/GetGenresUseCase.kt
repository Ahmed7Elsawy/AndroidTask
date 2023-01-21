package com.example.softxpert.task.domain.usecase

import com.example.softxpert.task.data.Resource
import com.example.softxpert.task.data.models.Genre
import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.domain.MoviesRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Resource<GenresResponse> {
        val movieResponse = moviesRepository.getGenres()
        val modifiedGenresList = movieResponse.data?.genres?.toMutableList()?.apply {
            map { it.isSelected = false }
            add(0, Genre(null, "All", true))
        }?.toList()

        modifiedGenresList?.let {
            movieResponse.data.genres = modifiedGenresList
        }
        return movieResponse
    }
}
