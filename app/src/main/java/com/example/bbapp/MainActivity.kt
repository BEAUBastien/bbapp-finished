package com.example.bbapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import coil.compose.rememberImagePainter




import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

import com.example.bbapp.ui.theme.BbappTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewmodel: MainViewModel by viewModels()
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val destinations = listOf(Destination.Profil, Destination.Films, Destination.Series, Destination.Acteurs)
            val windowSizeClass = calculateWindowSizeClass(this)
            BbappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if (currentDestination?.hierarchy?.any { it.route == Destination.Profil.destination } != true) {
                                BottomNavigation {
                                    destinations.forEach { screen ->
                                        BottomNavigationItem(
                                            icon = { Icon(screen.icon, contentDescription = null) },
                                            label = { Text(screen.label) },
                                            selected =
                                            currentDestination?.hierarchy?.any { it.route == screen.destination } == true,
                                            onClick = { navController.navigate(screen.destination) })
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHost(navController, startDestination = Destination.Profil.destination,
                            Modifier.padding(innerPadding)) {
                            composable(Destination.Profil.destination) { Profil(windowSizeClass, { navController.navigate("films") }) }

                            composable(Destination.Films.destination) { Films(viewmodel, navController) }
                            composable("filmDetails/{filmId}") { backStackEntry ->
                                val filmId = backStackEntry.arguments?.getString("filmId")?.toInt() ?: return@composable
                                FilmDetail(navController, viewmodel, filmId)
                            }
                            composable("genreScreen/{genreId}") { backStackEntry ->
                                val genreId = backStackEntry.arguments?.getString("genreId")?.toInt() ?: return@composable
                                GenreScreen(navController, viewmodel, genreId)
                            }
                            composable(Destination.Series.destination) { Series(viewmodel, navController)}
                            composable("serieDetails/{serieId}") { backStackEntry ->
                                val serieId = backStackEntry.arguments?.getString("serieId")?.toInt() ?: return@composable
                                SerieDetail(navController, viewmodel, serieId)
                            }
                            composable("genreScreenSerie/{genreId}") { backStackEntry ->
                                val genreId = backStackEntry.arguments?.getString("genreId")?.toInt() ?: return@composable
                                GenreScreenSerie(navController, viewmodel, genreId)
                            }


                            composable(Destination.Acteurs.destination) {ActorsScreen(viewmodel, navController) }
                            composable("actorDetails/{actorId}") { backStackEntry ->
                                val actorId = backStackEntry.arguments?.getString("actorId")?.toInt() ?: return@composable
                                ActorDetails(navController, viewmodel, actorId)
                            }



                        }
                    }
                }
            }
        }
    }
}



sealed class Destination(val destination: String, val label: String, val icon: ImageVector) {
    object Profil : Destination("profil", "Profil", Icons.Filled.Person)
    object Films : Destination("films", "Films", Icons.Filled.Edit)
    object Acteurs : Destination("acteurs", "acteurs", Icons.Filled.Group)
    object Series : Destination ("series", "series", Icons.Filled.Tv)
}



@Composable
fun Profil(windowClass: WindowSizeClass, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        if (windowClass.widthSizeClass == WindowWidthSizeClass.Compact) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.babas),
                    contentDescription = "Bastien BEAU a Primavera Sound 2023",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(350.dp)
                )
                Text(text = "Bastien BEAU", Modifier.padding(16.dp))
                Text(text = "Responsable SEO chez Yoopies")
                Text(text = "Étudiant en 3ᵉ années du BUT MMI")

                MyButton(onClick = onClick)
            }
        }
    }
}




@Composable
fun MyButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(150.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(text = "Démarrer")
        }
    }
}
@Composable
fun Films(viewmodel: MainViewModel, navController: NavHostController) {


    LaunchedEffect(key1 = true, block = { viewmodel.getMovies() })

    val movies by viewmodel.movies.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(movies) { movie ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("filmDetails/${movie.id}") },
                    elevation = 4.dp,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column {
                        val painter = rememberImagePainter(
                            data = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${movie.poster_path}",
                            builder = {
                                crossfade(true)
                            }
                        )

                        Image(
                            painter = painter,
                            contentDescription = movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(shape = MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = movie.title,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun FilmDetail(navController: NavHostController, viewModel: MainViewModel, filmId: Int) {

    val movieDetails by viewModel.movieDetails.collectAsState()

    LaunchedEffect(filmId) {
        viewModel.getMovieDetails(filmId)
    }

    val movie = movieDetails
    if (movie != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = movie.title,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
             Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w500/${movie.poster_path}"
                ),
                contentDescription = "Poster de ${movie.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                movie.genres.forEach { genre ->

                    GenreTag(genre.name) {

                        navController.navigate("genreScreen/${genre.id}")
                    }
                }
            }
            Text(
                text = "Date de sortie: ${movie.release_date}",
                style = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = movie.overview,
                style = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier.padding(top = 16.dp)
            )


        }
    }
}

@Composable
fun GenreTag(name: String, onClick: () -> Unit) {
    Card(
        backgroundColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(8.dp),
            color = Color.White,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
    }
}
@Composable
fun GenreScreen(navController: NavHostController, viewModel: MainViewModel, genreId: Int) {

    LaunchedEffect(genreId) {
        viewModel.getMoviesByGenre(genreId)
    }


    val moviesByGenre by viewModel.moviesByGenre.collectAsState()


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(moviesByGenre) { movie ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { navController.navigate("filmDetails/${movie.id}") },
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column {
                    val painter = rememberImagePainter(
                        data = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${movie.poster_path}",
                        builder = {
                            crossfade(true)
                        }
                    )

                    Image(
                        painter = painter,
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(shape = MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = movie.title,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Series(viewmodel: MainViewModel, navController: NavHostController) {

    LaunchedEffect(key1 = true, block = { viewmodel.getSeries() })

    val series by viewmodel.series.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
          items(series) { serie ->
              Card(
                  modifier = Modifier
                      .fillMaxWidth()
                      .padding(8.dp)
                      .clickable { navController.navigate("serieDetails/${serie.id}") },
                  elevation = 4.dp,
                  shape = RoundedCornerShape(12.dp)
              ){

                      Column {
                          val painter = rememberImagePainter(
                              data = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${serie.poster_path}",
                              builder = {
                                  crossfade(true)
                              }
                          )

                          Image(
                              painter = painter,
                              contentDescription = serie.title,
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .height(200.dp)
                                  .clip(shape = MaterialTheme.shapes.medium),
                              contentScale = ContentScale.Crop
                          )

                          Text(
                              text = serie.name,
                              style = TextStyle(
                                  fontSize = 18.sp,
                                  fontWeight = FontWeight.Bold,
                                  color = Color.Black
                              ),
                              modifier = Modifier
                                  .padding(8.dp)
                          )
                      }
                  }
              }


            }
        }
    }
@Composable
fun SerieDetail(navController: NavHostController, viewModel: MainViewModel, serieId: Int) {

    val serieDetails by viewModel.serieDetails.collectAsState()

    LaunchedEffect(serieId) {
        viewModel.getSerieDetails(serieId)
    }

    val serie = serieDetails
    if (serie != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {


            Text(
                text = serie.name,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w500/${serie.poster_path}"
                ),
                contentDescription = "Poster de ${serie.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                serie.genres.forEach { genre ->
                    // Provide an onClick lambda function for GenreTag
                    GenreTag(genre.name) {
                        // Action to be taken when the genre tag is clicked
                        navController.navigate("genreScreenSerie/${genre.id}")
                    }
                }
            }
            Text(
                text = "Date de sortie: ${serie.first_air_date}",
                style = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = serie.overview,
                style = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier.padding(top = 16.dp)
            )


        }
    }
}

@Composable
fun GenreScreenSerie(navController: NavHostController, viewModel: MainViewModel, genreId: Int) {

    LaunchedEffect(genreId) {
        viewModel.getSeriesByGenre(genreId)
    }


    val seriesByGenre by viewModel.seriesByGenre.collectAsState()


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(seriesByGenre) { serie ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { navController.navigate("serieDetails/${serie.id}") },
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column {
                    val painter = rememberImagePainter(
                        data = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${serie.poster_path}",
                        builder = {
                            crossfade(true)
                        }
                    )

                    Image(
                        painter = painter,
                        contentDescription = serie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(shape = MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = serie.name,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ActorsScreen(viewModel: MainViewModel, navController: NavHostController) {

    LaunchedEffect(Unit) {
        viewModel.getTrendingActors()
    }


    val actors by viewModel.trendingActors.collectAsState()


    LazyColumn {
        items(actors) { actor ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { navController.navigate("actorDetails/${actor.id}") },
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    val imageUrl = "https://image.tmdb.org/t/p/w500${actor.profile_path}"
                    val painter = rememberImagePainter(data = imageUrl, builder = {
                        crossfade(true)
                    })

                    Image(
                        painter = painter,
                        contentDescription = "Photo de l'acteur",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = actor.name,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun ActorDetails(navController: NavHostController, viewModel: MainViewModel, actorId: Int) {

    val actorDetails by viewModel.actorDetails.collectAsState()
    val actorMovies by viewModel.actorMovies.collectAsState()

    LaunchedEffect(actorId) {
        viewModel.getActorDetails(actorId)
        viewModel.getActorMovies(actorId)
    }

    val actor = actorDetails
    if (actor != null) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = rememberImagePainter(
                            data = "https://image.tmdb.org/t/p/w500/${actor.profile_path}"
                        ),
                        contentDescription = "Poster de ${actor.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Text(text = actor.name)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Date de naissance: ${actor.birthday}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Lieu de naissance: ${actor.place_of_birth}")
                    Spacer(modifier = Modifier.height(8.dp))

                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    Text(text = "Filmographie")
                }
            }

            items(actorMovies) { cast ->
                MovieItem(cast, navController)
            }
        }
    }
}


@Composable
fun MovieItem(cast: CastMember, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {navController.navigate("filmDetails/${cast.id}")},
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${cast.poster_path}"
            val painter = rememberImagePainter(data = imageUrl, builder = {
                crossfade(true)
            })

            Image(
                painter = painter,
                contentDescription = "Poster de ${cast.title}",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = cast.title,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}




