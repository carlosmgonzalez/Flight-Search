package com.carlosmgonzalez.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosmgonzalez.flightsearch.data.local.AirportEntity
import com.carlosmgonzalez.flightsearch.data.local.FavoriteEntity
import com.carlosmgonzalez.flightsearch.data.repositories.FlightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val flightRepository: FlightRepository
): ViewModel() {
    val allAirports: StateFlow<List<AirportEntity>> = flightRepository.getAllAirports()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    val allFavoriteFlights: StateFlow<List<FavoriteEntity>> = flightRepository.getAllFavoriteFlights()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    fun searchAirport(searchTerm: String): StateFlow<List<AirportEntity>> {
        return flightRepository.searchAirport(searchTerm).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )
    }

    fun saveFlightToFavorite(flight: FavoriteEntity) {
        viewModelScope.launch {
            flightRepository.saveFlightToFavorite(flight)
        }
    }

    fun deleteFlightFromFavorite(flight: FavoriteEntity) {
        viewModelScope.launch {
            flightRepository.deleteFlightFromFavorite(flight)
        }
    }
}

//data class AirportsUiState(
//    val allAirports: List<AirportEntity> = emptyList()
//)