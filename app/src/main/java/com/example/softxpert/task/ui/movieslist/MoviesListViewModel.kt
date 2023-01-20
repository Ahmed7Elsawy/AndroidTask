package com.example.softxpert.task.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softxpert.task.data.Resource
import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.domain.usecase.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
) : ViewModel() {

    private val _genresResponse: MutableLiveData<Resource<GenresResponse>> =
        MutableLiveData(Resource.Loading())
    val genresResponse: LiveData<Resource<GenresResponse>> = _genresResponse


    init {
        getGenres()
    }

    private fun getGenres() {
        viewModelScope.launch {
            val response = getGenresUseCase()
            _genresResponse.value = response
        }
    }

    fun selectGenre(genreId: Int?) {
        viewModelScope.launch {
            val genres =
                genresResponse.value?.data?.genres?.map { genre -> genre.copy(isSelected = genre.id == genreId) }

            genres?.let {
                    genresResponse.value?.apply { this.data?.genres = genres }?.let {
                        _genresResponse.value = it
                    }
            }
        }
    }

}
