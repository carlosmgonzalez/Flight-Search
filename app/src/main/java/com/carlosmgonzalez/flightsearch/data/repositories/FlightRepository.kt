package com.carlosmgonzalez.flightsearch.data.repositories

import com.carlosmgonzalez.flightsearch.data.local.AirportEntity
import com.carlosmgonzalez.flightsearch.data.local.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    fun searchAirport(searchTerm: String): Flow<List<AirportEntity>>

    fun getAllAirports(): Flow<List<AirportEntity>>

    suspend fun saveFlightToFavorite(flight: FavoriteEntity)

    fun getAllFavoriteFlights(): Flow<List<FavoriteEntity>>

    suspend fun deleteFlightFromFavorite(flight: FavoriteEntity)
}