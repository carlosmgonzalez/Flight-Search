package com.carlosmgonzalez.flightsearch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {
    @Query("select * from airport where name like '%' || :searchTerm || '%' or iata_code like '%' || :searchTerm || '%'")
    fun searchAirport(searchTerm: String): Flow<List<AirportEntity>>

    @Query("select * from airport")
    fun getAllAirports(): Flow<List<AirportEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveFlightToFavorite(flight: FavoriteEntity)

    @Query("select * from favorite")
    fun getAllFavoriteFlights(): Flow<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFlightFromFavorite(flight: FavoriteEntity)
}