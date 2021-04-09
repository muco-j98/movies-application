package com.example.movies_application.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_application.network.models.MoviesItem
import com.example.movies_application.network.util.Resource
import com.example.movies_application.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    val moviesResponse: MutableLiveData<Resource<List<MoviesItem>>> = MutableLiveData()

    fun getMovies() = viewModelScope.launch {
        moviesResponse.postValue(Resource.Loading())
        mainRepository.getMovies().let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    moviesResponse.postValue(Resource.Success(it))
                }
            } else {
                moviesResponse.postValue(Resource.Error(response.message()))
            }
        }
    }
}