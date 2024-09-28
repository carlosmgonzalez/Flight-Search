package com.carlosmgonzalez.flightsearch.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class AirportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo("iata_code")
    val iataCode: String,
    val passengers: Int
)

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("departure_code")
    val departureCode: String,
    @ColumnInfo("destination_code")
    val destinationCode: String
)