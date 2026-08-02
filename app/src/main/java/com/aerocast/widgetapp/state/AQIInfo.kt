package com.aerocast.widgetapp.state

import kotlinx.serialization.Serializable

@Serializable
sealed interface AQIInfo {
    @Serializable
    object Loading : AQIInfo

    @Serializable
    data class Available(
        val currentAqi: Int,
        val forecastAqi: List<Int>
    ) : AQIInfo

    @Serializable
    data class Unavailable(val message: String) : AQIInfo
}