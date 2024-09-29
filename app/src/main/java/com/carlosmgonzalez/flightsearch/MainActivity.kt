package com.carlosmgonzalez.flightsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.carlosmgonzalez.flightsearch.ui.FlightApp
import com.carlosmgonzalez.flightsearch.ui.theme.FlightSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightSearchTheme(darkTheme = false) {
                FlightApp()
            }
        }
    }
}