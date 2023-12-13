package com.example.bbapp

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("trending/movie/week")
    suspend fun lastmovies(@Query("api_key") api_key: String): TmdbMovieResult

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "fr-FR"
    ): TMDBMovieDetails

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int,
        @Query("language") language: String = "en-US"
    ): TmdbMovieResult

    @GET("trending/tv/week")
    suspend fun lastseries(@Query("api_key") apiKey: String): TmdbSerieResult

    @GET("tv/{serie_id}")
    suspend fun getSerieDetails(
        @Path("serie_id") serieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "fr-FR"
    ): TMDBSerieDetails

    @GET("discover/tv")
    suspend fun getSeriesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int,
        @Query("language") language: String = "en-US"
    ): TmdbSerieResult

    @GET("trending/person/week")
    suspend fun getTrendingActors(@Query("api_key") apiKey: String): TmdbActorResult
    @GET("person/{actor_id}")
    suspend fun getActorDetails(
        @Path("actor_id") actorId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "fr-FR"
    ): TMDBActorDetails

    @GET("person/{actor_id}/movie_credits")
    suspend fun getActorMovies(
        @Path("actor_id") actorId: Int,
        @Query("api_key") apiKey: String
    ): MovieCast // Pas sure


}