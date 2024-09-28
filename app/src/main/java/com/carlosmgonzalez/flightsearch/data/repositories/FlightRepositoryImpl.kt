package com.carlosmgonzalez.flightsearch.data.repositories

import com.carlosmgonzalez.flightsearch.data.local.AirportEntity
import com.carlosmgonzalez.flightsearch.data.local.FavoriteEntity
import com.carlosmgonzalez.flightsearch.data.local.FlightDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FlightRepositoryImpl @Inject constructor(
    private val flightDao: FlightDao
): FlightRepository {
    override fun searchAirport(
        searchTerm: String
    ): Flow<List<AirportEntity>> {
        return flightDao.searchAirport(searchTerm = searchTerm)
    }

    override fun getAllAirports(): Flow<List<AirportEntity>> {
        return flightDao.getAllAirports()
    }

    override suspend fun saveFlightToFavorite(flight: FavoriteEntity) {
        flightDao.saveFlightToFavorite(flight)
    }

    override fun getAllFavoriteFlights(): Flow<List<FavoriteEntity>> {
        return flightDao.getAllFavoriteFlights()
    }

    override suspend fun deleteFlightFromFavorite(flight: FavoriteEntity) {
        flightDao.deleteFlightFromFavorite(flight)
    }
}