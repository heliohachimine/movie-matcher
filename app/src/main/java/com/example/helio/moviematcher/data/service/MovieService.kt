package com.example.helio.moviematcher.data.service

import com.example.helio.moviematcher.data.response.GenreResult
import com.example.helio.moviematcher.data.response.KeywordResult
import com.example.helio.moviematcher.data.response.MovieResponse
import com.example.helio.moviematcher.data.response.MovieResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MovieResult

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): MovieResult

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): MovieResult

    @GET("discover/movie")
    suspend fun getMoviesByGenre(@Query("page") page: Int,  @Query("with_genres") genreId: String?): MovieResult

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResult

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Long): MovieResponse

    @GET("movie/{movie_id}/keywords")
    suspend fun getMovieKeywords(@Path("movie_id") id: Long): KeywordResult
}