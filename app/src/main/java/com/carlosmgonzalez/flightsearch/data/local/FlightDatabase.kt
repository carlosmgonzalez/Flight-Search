package com.carlosmgonzalez.flightsearch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteEntity::class, AirportEntity::class],
    version = 3,
    exportSchema = false
)
abstract class FlightDatabase: RoomDatabase() {
    abstract fun flightDao(): FlightDao

//    companion object {
//        @Volatile
//        private var Instance: FlightDatabase? = null
//
//        fun getDatabase(context: Context): FlightDatabase {
//            return Instance ?: synchronized(this) {
//                Room.databaseBuilder(
//                    context = context,
//                    klass = FlightDatabase::class.java,
//                    name = "flight_database"
//                )
//                    .fallbackToDestructiveMigration()
////                    .createFromAsset("database/flight_search.db")
//                    .build()
//                    .also { Instance = it }
//            }
//        }
//    }
}