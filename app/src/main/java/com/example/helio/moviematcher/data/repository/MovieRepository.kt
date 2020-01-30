package com.example.helio.moviematcher.data.repository

import com.example.helio.moviematcher.data.response.GenreResult
import com.example.helio.moviematcher.data.response.KeywordResult
import com.example.helio.moviematcher.data.response.MovieResponse
import com.example.helio.moviematcher.data.response.MovieResult
import com.example.helio.moviematcher.data.service.MoviesService

class MovieRepository(private val service: MoviesService) {

    suspend fun getPopularMovies(page: Int): MovieResult {
        return service.getPopularMovies(page)
    }

    suspend fun getMoviesByGenre(page: Int, genreId: String?): MovieResult {
        return service.getMoviesByGenre(page, genreId)
    }

    suspend fun getTopRatedMovies(page: Int): MovieResult {
        return service.getTopRatedMovies(page)
    }

    suspend fun getUpcomingMovies(page: Int): MovieResult {
        return service.getUpcomingMovies(page)
    }

    suspend fun getGenres(): GenreResult {
        return service.getGenres()
    }

    suspend fun getMovie(id: Long): MovieResponse {
        return service.getMovieDetails(id)
    }

    suspend fun getMovieKeywords(id: Long): KeywordResult {
        return service.getMovieKeywords(id)
    }
}

