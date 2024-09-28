package com.carlosmgonzalez.flightsearch.di

import android.content.Context
import androidx.room.Room
import com.carlosmgonzalez.flightsearch.data.local.FlightDao
import com.carlosmgonzalez.flightsearch.data.local.FlightDatabase
import com.carlosmgonzalez.flightsearch.data.repositories.FlightRepository
import com.carlosmgonzalez.flightsearch.data.repositories.FlightRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFlightDatabase(
        @ApplicationContext context: Context
    ): FlightDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = FlightDatabase::class.java,
            name = "flight_database"
        )
            .createFromAsset("database/flight_search.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideFlightDao(
        flightDatabase: FlightDatabase
    ): FlightDao {
        return flightDatabase.flightDao()
    }

    @Provides
    @Singleton
    fun provideFlightRepository(
        flightDao: FlightDao
    ): FlightRepository {
        return FlightRepositoryImpl(flightDao)
    }
}