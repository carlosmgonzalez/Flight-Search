package com.carlosmgonzalez.flightsearch.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.carlosmgonzalez.flightsearch.data.local.AirportEntity
import com.carlosmgonzalez.flightsearch.data.local.FavoriteEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightApp() {
    val flightViewModel: FlightViewModel = hiltViewModel<FlightViewModel>()

    var textFieldFocused by remember { mutableStateOf(false) }
    val setTextFieldFocused = { focused: Boolean -> textFieldFocused = focused }
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    if (textFieldFocused) {
                        focusManager.clearFocus()
                    }
                }
            },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Flight Search", color = Color(0xffffffff))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF195AAD),
                    titleContentColor = Color(0xffffffff),
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding() + 15.dp)
                .padding(horizontal = 15.dp)
        ) {
            SearchAndShowFlight(
                setTextFieldFocused = setTextFieldFocused,
                flightViewModel = flightViewModel,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun SearchAndShowFlight(
    setTextFieldFocused: (focused: Boolean) -> Unit,
    flightViewModel: FlightViewModel,
    modifier: Modifier = Modifier
) {
    val allAirports by flightViewModel.allAirports.collectAsState()
    val allFavoriteFlight by flightViewModel.allFavoriteFlights.collectAsState()

    var selectedAirport: AirportEntity? by rememberSaveable { mutableStateOf(null) }
    val setSelectedAirport = { airport: AirportEntity? -> selectedAirport = airport }

    var airportsFound: StateFlow<List<AirportEntity>> by remember {
        mutableStateOf(
            MutableStateFlow(
                emptyList()
            )
        )
    }
    val setAirportsFound = { airports: StateFlow<List<AirportEntity>> -> airportsFound = airports }

    var searchTerm by rememberSaveable { mutableStateOf("") }
    val setSearchTerm = { term: String -> searchTerm = term }

    val airports = airportsFound.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged {
                    setTextFieldFocused(it.isFocused)
                }
                .height(55.dp),
            value = searchTerm,
            onValueChange = {
                setSearchTerm(it)
                if (searchTerm.isEmpty()) {
                    setAirportsFound(MutableStateFlow(emptyList()))
                } else {
                    setSelectedAirport(null)
                    val results = flightViewModel.searchAirport(searchTerm)
                    setAirportsFound(results)
                }
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    val flightFound = flightViewModel.searchAirport(searchTerm)
                    setAirportsFound(flightFound)
                }
            ),
            shape = RoundedCornerShape(30.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFF7FA9FC).copy(alpha = 0.5f),
                focusedContainerColor = Color(0xFF7FA9FC).copy(alpha = 0.6f),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "mic"
                )
            }
        )
        Spacer(Modifier.height(10.dp))
        LazyColumn {
            items(
                items = airports.value,
                key = { airport -> airport.id }
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            setSearchTerm(it.iataCode)
                            setAirportsFound(MutableStateFlow(emptyList()))
                            setSelectedAirport(it)
                            Log.i("selectedAirport", selectedAirport?.name ?: "No selected")
                        }
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                ) {
                    Text(it.iataCode, fontWeight = FontWeight.Bold)
                    Text("  ")
                    Text(it.name)
                }
            }
        }
        ListFlightsFound(
            selectedAirport = selectedAirport,
            allAirports = allAirports,
            allFavoriteFlight = allFavoriteFlight,
            saveFlightToFavorite = {
                flightViewModel.saveFlightToFavorite(it)
            },
            deleteFlightFromFavorite = {
                flightViewModel.deleteFlightFromFavorite(it)
            }
        )
        AnimatedVisibility(searchTerm.isEmpty()) {
            ListFavoriteFlights(
                allFavoriteFlight = allFavoriteFlight,
                allAirports = allAirports,
                deleteFlightFromFavorite = {
                    flightViewModel.deleteFlightFromFavorite(it)
                }
            )
        }
    }
}

//@Composable
//fun FlightSearch(
//    modifier: Modifier = Modifier,
//    searchTerm: String,
//    setSearchTerm: (term: String) -> Unit,
//    setTextFieldFocused: (focused: Boolean) -> Unit,
//    setAirportsFound: (airports: StateFlow<List<AirportEntity>>) -> Unit,
//    setSelectedAirport: (airport: AirportEntity?) -> Unit,
//    flightViewModel: FlightViewModel,
//    airports: State<List<AirportEntity>>,
//    selectedAirport: AirportEntity?
//) {
//}

@Composable
fun ListFlightsFound(
    selectedAirport: AirportEntity?,
    allAirports: List<AirportEntity>,
    allFavoriteFlight: List<FavoriteEntity>,
    saveFlightToFavorite: (flight: FavoriteEntity) -> Unit,
    deleteFlightFromFavorite: (flight: FavoriteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(selectedAirport != null) {
        if (selectedAirport == null) return@AnimatedVisibility
        Column(modifier = modifier.fillMaxSize()) {
            Text(
                text = "Flights from ${selectedAirport.iataCode}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            LazyColumn {
                items(
                    items = allAirports,
                    key = { it.id }
                ) { airport ->
                    if (airport.id != selectedAirport.id) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                bottomEnd = 0.dp,
                                topEnd = 10.dp,
                                bottomStart = 10.dp
                            )
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                val flightInFavorite = allFavoriteFlight.find { flight ->
                                    flight.departureCode == selectedAirport.iataCode
                                            && flight.destinationCode == airport.iataCode
                                }
                                IconButton(
                                    onClick = {
                                        if (flightInFavorite != null) {
                                            deleteFlightFromFavorite(flightInFavorite)
                                        } else {
                                            saveFlightToFavorite(
                                                FavoriteEntity(
                                                    departureCode = selectedAirport.iataCode,
                                                    destinationCode = airport.iataCode
                                                )
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .offset(x = -(10).dp, y = 10.dp)

                                ) {
                                    if (flightInFavorite != null) {
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color(0xFF195AAD)
                                        )
                                    } else {
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null
                                        )
                                    }
                                }

                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text("DEPART", fontSize = 13.sp, color = Color(0xFF656565))
                                    Row {
                                        Text(
                                            selectedAirport.iataCode,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text("  ")
                                        Text(selectedAirport.name)
                                    }
                                    Spacer(Modifier.height(3.dp))
                                    Text("ARRIVE", fontSize = 13.sp, color = Color(0xFF656565))
                                    Row {
                                        Text(airport.iataCode, fontWeight = FontWeight.Bold)
                                        Text("  ")
                                        Text(airport.name)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListFavoriteFlights(
    allFavoriteFlight: List<FavoriteEntity>,
    allAirports: List<AirportEntity>,
    deleteFlightFromFavorite: (flight: FavoriteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Favorite Flights",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        LazyColumn {
            items(
                items = allFavoriteFlight,
                key = { it.id }
            ) { flight ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomEnd = 0.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp
                    )
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = { deleteFlightFromFavorite(flight) },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = -(10).dp, y = 10.dp)

                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFF195AAD)
                            )
                        }

                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text("DEPART", fontSize = 13.sp, color = Color(0xFF656565))
                            Row {
                                Text(
                                    flight.departureCode,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("  ")
                                Text(
                                    allAirports.find { airport -> airport.iataCode == flight.departureCode }?.name
                                        ?: ""
                                )
                            }
                            Spacer(Modifier.height(3.dp))
                            Text("ARRIVE", fontSize = 13.sp, color = Color(0xFF656565))
                            Row {
                                Text(flight.destinationCode, fontWeight = FontWeight.Bold)
                                Text("  ")
                                Text(
                                    allAirports.find { airport -> airport.iataCode == flight.destinationCode }?.name
                                        ?: ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
