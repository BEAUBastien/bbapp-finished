package com.example.bbapp

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class TmdbMovieResult(
    var page: Int = 0,
    val results: List<TmdbMovie> = listOf())

class TmdbMovie(
    var overview: String = "",
    val release_date: String = "",
    val id: String = "",
    val title: String = "",
    val original_title: String = "",
    val backdrop_path: String? = "",
    val genre_ids: List<Int> = listOf(),
    val poster_path: String? = "")

data class TMDBMovieDetails(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val belongs_to_collection: Any = Any(),
    val budget: Int = 0,
    val homepage: String = "",
    val id: Int = 0,
    val imdb_id: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val genres: List<Genre> = listOf(),
    val revenue: Int = 0,
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

data class Genre(
    val id: Int,
    val name: String
)

class TmdbSerieResult(
    var page: Int = 0,
    val results: List<TmdbSeries> = listOf())
class TmdbSeries(
    var name: String = "",
    val first_air_date: String = "",
    val id: String = "",
    val title: String = "",
    val original_title: String = "",
    val backdrop_path: String? = "",
    val genre_ids: List<Int> = listOf(),
    val poster_path: String? = "") {
}

data class TMDBSerieDetails(
    val id: Int,
    val name: String,
    val original_name: String,
    val overview: String,
    val genres: List<Genre>,
    val first_air_date: String,
    val last_air_date: String?,
    val number_of_episodes: Int?,
    val number_of_seasons: Int?,
    val poster_path: String?,
    val backdrop_path: String?,
    val popularity: Double,
    val vote_average: Double,
    val vote_count: Int,
    val status: String
)

class TmdbActorResult(
    var page: Int = 0,
    val results: List<TmdbActor> = listOf())

data class TmdbActor(
    val adult: Boolean,
    val id: Int,
    val name: String,
    val original_name: String,
    val media_type: String,
    val popularity: Double,
    val gender: Int,
    val known_for_department: String,
    val profile_path: String?,
)

data class TMDBActorDetails(
    val adult: Boolean,
    val also_known_as: List<String>,
    val biography: String,
    val birthday: String,
    val deathday: String?, // Nullable because it can be null
    val gender: Int,
    val homepage: String?,
    val id: Int,
    val imdb_id: String,
    val known_for_department: String,
    val name: String,
    val place_of_birth: String,
    val popularity: Double,
    val profile_path: String
)

data class MovieCast(
    val cast: List<CastMember> = listOf()
)

data class CastMember(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val character: String,
    val creditId: String,
    val order: Int
)

