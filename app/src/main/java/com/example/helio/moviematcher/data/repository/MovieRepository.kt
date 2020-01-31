package com.example.helio.moviematcher.data.repository

import com.example.helio.moviematcher.data.response.*
import com.example.helio.moviematcher.data.service.MoviesService

class MovieRepository(private val service: MoviesService) {

    companion object{
        const val LANGUAGE = "pt-BR"
    }

    suspend fun getPopularMovies(page: Int): MovieResult {
        return service.getPopularMovies(page, LANGUAGE)
    }

    suspend fun getMoviesByGenre(page: Int, genreId: String?): MovieResult {
        return service.getMoviesByGenre(page, genreId, LANGUAGE)
    }

    suspend fun getTopRatedMovies(page: Int): MovieResult {
        return service.getTopRatedMovies(page, LANGUAGE)
    }

    suspend fun getUpcomingMovies(page: Int): MovieResult {
        return service.getUpcomingMovies(page, LANGUAGE)
    }

    suspend fun getGenres(): GenreResult {
        return service.getGenres(LANGUAGE)
    }

    suspend fun getMovie(id: Long): MovieResponse {
        return service.getMovieDetails(id, LANGUAGE)
    }

    suspend fun getMovieKeywords(id: Long): KeywordResult {
        return service.getMovieKeywords(id)
    }

    suspend fun getMovieImages(id: Long): ImagesResult {
        return service.getMovieImages(id)
    }
}

