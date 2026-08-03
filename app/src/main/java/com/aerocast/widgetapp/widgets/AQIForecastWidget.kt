package com.aerocast.widgetapp.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.background
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.getAppWidgetState
import androidx.glance.layout.*
import androidx.glance.text.*
import androidx.glance.unit.ColorProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.aerocast.widgetapp.R
import com.aerocast.widgetapp.utils.createCustomTextBitmap
import com.aerocast.widgetapp.utils.createPillTextBitmap
import com.aerocast.widgetapp.state.AQIInfo
import com.aerocast.widgetapp.state.AQIInfoStateDefinition
import android.graphics.Bitmap


class AQIForecastWidget : GlanceAppWidget() {

    override val stateDefinition = AQIInfoStateDefinition

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        val state = getAppWidgetState(
            context = context,
            definition = AQIInfoStateDefinition,
            glanceId = id
        )

        provideContent {
            when(state) {
                is AQIInfo.Loading -> {
                    MyWidgetContent(
                        context,
                        null,
                        null
                    )
                }

                is AQIInfo.Available -> {
                    MyWidgetContent(
                        context,
                        state.currentAqi,
                        state.forecastAqi
                    )
                }

                is AQIInfo.Unavailable -> {
                    MyWidgetContent(
                        context,
                        null,
                        null
                    )
                }
            }
        }
    }
}

@Composable
fun MyWidgetContent(context: Context, currentAqi: Int?, forecastAqi: List<Int>?) {
    val category = when (currentAqi) {
        null -> "..."
        in 0..50 -> "Good"
        in 51..100 -> "Moderate"
        in 101..150 -> "Unhealthy for Sensitive Groups"
        in 151..200 -> "Unhealthy"
        in 201..300 -> "Very Unhealthy"
        else -> "Hazardous"
    }

    fun getAqiCategory(aqi: Int?): String {
        return when (aqi) {
            null -> "-"
            in 0..50 -> "Good"
            in 51..100 -> "Moderate"
            in 101..150 -> "Unhealthy for Sensitive Groups"
            in 151..200 -> "Unhealthy"
            in 201..300 -> "Very Unhealthy"
            else -> "Hazardous"
        }
    }

    val currentAqiColor = when (currentAqi) {
        null -> Color(0xFF00361C)
        in 0..50 -> Color(0xFF00361C) // Green
        in 51..100 -> Color(0xB87801) // Yellow
        in 101..150 -> Color(0xB95600) // Orange
        in 151..200 -> Color(0xB10909) // Red
        in 201..300 -> Color(0x5E0063) // Purple
        else -> Color(0xA39E9E) // Gray
    }

    fun getAqiColor(aqi: Int?): Color {
        return when (aqi) {
            null -> Color(0xFF00361C)
            in 0..50 -> Color(0xFF00361C)
            in 51..100 -> Color(0xB87801)
            in 101..150 -> Color(0xB95600)
            in 151..200 -> Color(0xB10909)
            in 201..300 -> Color(0x5E0063)
            else -> Color(0xA39E9E)
        }
    }

    @Composable
    fun row_gap() {
        // Spacer( modifier = GlanceModifier.width(1.dp) )
        // Spacer(
        //     modifier = GlanceModifier
        //         .width(1.dp)
        //         .height(75.dp)
        //         .background(ColorProvider(Color.Gray))
        // )
        // Spacer( modifier = GlanceModifier.width(4.dp) )
    }

    @Composable
    fun row_items(weekday: String, date: String, aqi: Int?, category: String, aqiColor: Color) {
        Column(
            modifier = GlanceModifier
                .padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Image(
                contentDescription = null,
                provider = ImageProvider(
                    createCustomTextBitmap(
                        context = context,
                        text = weekday,
                        textColor = aqiColor,
                        fontSize = 16f,
                        fontFamily = "fonts/SF-Pro-Rounded-Heavy.ttf",
                    )
                )
            )

            Spacer( modifier = GlanceModifier.height(2.dp) )

            Image(
                contentDescription = null,
                provider = ImageProvider(
                    createCustomTextBitmap(
                        context = context,
                        text = date,
                        textColor = aqiColor,
                        fontSize = 12f,
                        fontFamily = "fonts/SF-Pro-Regular.ttf",
                    )
                )
            )

            Spacer( modifier = GlanceModifier.height(6.dp) )

            Image(
                contentDescription = null,
                provider = ImageProvider(
                    createCustomTextBitmap(
                        context = context,
                        text = aqi?.toString() ?: "-",
                        textColor = aqiColor,
                        fontSize = 27f,
                        fontFamily = "fonts/SF-Pro-Rounded-Heavy.ttf",
                    )
                )
            )

            Spacer( modifier = GlanceModifier.height(4.dp) )

            Image(
                contentDescription = null,
                provider = ImageProvider(
                    createCustomTextBitmap(
                        context = context,
                        text = category,
                        textColor = aqiColor,
                        fontSize = 12f,
                        fontFamily = "fonts/SF-Pro-Regular.ttf",
                    )
                )
            )
        }
    }

    Box(
        modifier = GlanceModifier.fillMaxSize()
    ) {

        Image(
            provider = ImageProvider(R.drawable.aqi_forecast_background),
            contentDescription = null,
            modifier = GlanceModifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Image(
                contentDescription = null,
                provider = ImageProvider(
                    createPillTextBitmap(
                        context = context,
                        text = "AQI Forecast in General Santos City",
                        textColor = Color.White,
                        backgroundColor = Color(0xFF00361C),
                        fontSize = 12f,
                        fontFamily = "fonts/SF-Pro-Regular.ttf",
                        horizontalPadding = 8,
                        verticalPadding = 4,
                        cornerRadius = 32f,
                    )
                )
            )

            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .wrapContentWidth()
            ) {
                row_items("MON", "Aug 3", currentAqi, category, currentAqiColor)

                row_gap()

                row_items("TUE", "Aug 4", forecastAqi?.getOrNull(0), getAqiCategory(forecastAqi?.getOrNull(0)), getAqiColor(forecastAqi?.getOrNull(0)))

                row_gap()

                row_items("WED", "Aug 5", forecastAqi?.getOrNull(1), getAqiCategory(forecastAqi?.getOrNull(1)), getAqiColor(forecastAqi?.getOrNull(1)))

                row_gap()

                row_items("THU", "Aug 6", forecastAqi?.getOrNull(2), getAqiCategory(forecastAqi?.getOrNull(2)), getAqiColor(forecastAqi?.getOrNull(2)))

                row_gap()

                row_items("FRI", "Aug 7", forecastAqi?.getOrNull(3), getAqiCategory(forecastAqi?.getOrNull(3)), getAqiColor(forecastAqi?.getOrNull(3)))

                row_gap()

                row_items("SAT", "Aug 8", forecastAqi?.getOrNull(4), getAqiCategory(forecastAqi?.getOrNull(4)), getAqiColor(forecastAqi?.getOrNull(4)))
            }
        }
    }
}