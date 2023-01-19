package com.example.softxpert.task.ui.usecase

import com.example.softxpert.task.data.Resource
import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.domain.MoviesRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Resource<GenresResponse> {
        return moviesRepository.getGenres()
    }

}
