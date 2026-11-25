package com.aerocast.widgetapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aerocast.widgetapp.data.AQIRepository
import kotlinx.coroutines.launch

class AQIForecastViewModel : ViewModel() {

    var forecast by mutableStateOf<List<Int>>(emptyList())
        private set

    init {
        loadForecast()
    }

    private fun loadForecast() {
        viewModelScope.launch {
            val response = AQIRepository.fetchAQI()
            forecast = response.forecastAqi
        }
    }
}