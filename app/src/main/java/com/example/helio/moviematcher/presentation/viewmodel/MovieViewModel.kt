package com.example.helio.moviematcher.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helio.moviematcher.data.repository.MovieRepository
import androidx.lifecycle.viewModelScope
import com.example.helio.moviematcher.data.response.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel(){

    val genresLiveData = MutableLiveData<List<GenreResponse>>()
    val moviesLiveData = MutableLiveData<List<MovieResponse>>()
    val movieImagesLiveData = MutableLiveData<ImagesResult>()
    val moviesByGenreLiveData = MutableLiveData<List<MovieResponse>>()
    val singleMovieLiveData = MutableLiveData<MovieResponse>()
    val movieKeywordsLiveData = MutableLiveData<List<KeywordResponse>>()
    val movieError : MutableLiveData<Throwable> = MutableLiveData()


    fun getGenresList() {
        viewModelScope.launch {
            kotlin.runCatching {
                val genres = async { repository.getGenres() }

                genresLiveData.postValue(genres.await().genres)
            }.onFailure {
                movieError.postValue(it)
            }
        }
    }

    fun getMoviesByGenre(page: Int, genreId: String?) {
        viewModelScope.launch {
            kotlin.runCatching {
                val movies = async { repository.getMoviesByGenre(page, genreId) }

                moviesByGenreLiveData.postValue(movies.await().movies)
            }.onFailure {
                movieError.postValue(it)
            }
        }
    }

    //TODO refatorar essa função
    fun getTopRatedMovies(page: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                val movies = async { repository.getPopularMovies(page) }

                moviesByGenreLiveData.postValue(movies.await().movies)
            }.onFailure {
                movieError.postValue(it)
            }
        }
    }

    fun getSingleMovie(id: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                val movie = async {repository.getMovie(id)}
                singleMovieLiveData.postValue(movie.await())
            }.onFailure {
                movieError.postValue(it)
            }
        }
    }

    fun getImagesMovie(id: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                val images = async {repository.getMovieImages(id)}
                movieImagesLiveData.postValue(images.await())
            }.onFailure {
                movieError.postValue(it)
            }
        }
    }

    fun getMovieKeywords(id: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                val keywords = async { repository.getMovieKeywords(id) }
                movieKeywordsLiveData.postValue(keywords.await().keywords)
            }.onFailure {
                movieError.postValue(it)
            }
        }
    }
}