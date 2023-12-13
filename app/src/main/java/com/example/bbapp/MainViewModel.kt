package com.example.bbapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory



class MainViewModel : ViewModel() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();
    val api = retrofit.create(Api::class.java)
    val movies = MutableStateFlow<List<TmdbMovie>>(listOf())
    val movieDetails = MutableStateFlow<TMDBMovieDetails?>(null)
    val moviesByGenre = MutableStateFlow<List<TmdbMovie>>(listOf())
    val seriesByGenre = MutableStateFlow<List<TmdbSeries>>(listOf())
    val series = MutableStateFlow<List<TmdbSeries>>(listOf())
    val serieDetails = MutableStateFlow<TMDBSerieDetails?>(null)
    val trendingActors = MutableStateFlow<List<TmdbActor>>(listOf())
    val actorDetails = MutableStateFlow<TMDBActorDetails?>(null)
    val actorMovies = MutableStateFlow<List<CastMember>>(listOf())



    fun getMovies() {
        viewModelScope.launch {
            movies.value = api.lastmovies("c36d23110d3cb6185a16058a84974221").results

        }
    }
    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            movieDetails.value = api.getMovieDetails(movieId, "c36d23110d3cb6185a16058a84974221",  "fr-FR")
        }
    }
    fun getMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            val response = api.getMoviesByGenre("c36d23110d3cb6185a16058a84974221", genreId, "fr-FR")
            moviesByGenre.value = response.results
        }
    }
    fun getSeries() {
        viewModelScope.launch {
            try {
                series.value = api.lastseries("c36d23110d3cb6185a16058a84974221").results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getSerieDetails(serieId: Int) {
        viewModelScope.launch {
            try {
                serieDetails.value = api.getSerieDetails(serieId, "c36d23110d3cb6185a16058a84974221", "fr-FR")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getSeriesByGenre(genreId: Int) {
        viewModelScope.launch {
            val response = api.getSeriesByGenre("c36d23110d3cb6185a16058a84974221", genreId, "fr-FR")
            seriesByGenre.value = response.results
        }
    }
    fun getTrendingActors() {
        viewModelScope.launch {
            try {
                val response = api.getTrendingActors("c36d23110d3cb6185a16058a84974221")
                trendingActors.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getActorDetails(actorId: Int) {
        viewModelScope.launch {
            try {
                actorDetails.value = api.getActorDetails(actorId, "c36d23110d3cb6185a16058a84974221", "fr-FR")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getActorMovies(actorId: Int) {
        viewModelScope.launch {
            try {
                val response = api.getActorMovies(actorId, "c36d23110d3cb6185a16058a84974221")
                Log.d("ActorMovies", "Movies: ${response.cast}")
                Log.d("ActorMoviesID", "Movies: $actorId")

                actorMovies.value = response.cast
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

