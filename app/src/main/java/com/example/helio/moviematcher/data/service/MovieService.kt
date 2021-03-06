package com.example.helio.moviematcher.data.service

import com.example.helio.moviematcher.data.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int,
                                 @Query("language") language: String): MovieResult

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int,
                                  @Query("language") language: String): MovieResult

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int,
                                  @Query("language") language: String): MovieResult

    @GET("discover/movie")
    suspend fun getMoviesByGenre(@Query("page") page: Int,
                                 @Query("with_genres") genreId: String?,
                                 @Query("language") language: String): MovieResult

    @GET("genre/movie/list")
    suspend fun getGenres( @Query("language") language: String): GenreResult

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Long,
                                @Query("language") language: String): MovieResponse

    @GET("movie/{movie_id}/keywords")
    suspend fun getMovieKeywords(@Path("movie_id") id: Long): KeywordResult

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(@Path("movie_id") id: Long): ImagesResult
}