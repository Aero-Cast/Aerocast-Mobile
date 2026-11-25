package com.aerocast.widgetapp.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
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
import com.aerocast.widgetapp.activity.AQIForecastActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
                    ForecastWidgetContent(
                        context,
                        null
                    )
                }

                is AQIInfo.Available -> {
                    android.util.Log.d(
                        "AQIForecastWidget",
                        "forecastAqi=${state.forecastAqi}"
                    )

                    ForecastWidgetContent(
                        context,
                        state.forecastAqi
                    )
                }

                is AQIInfo.Unavailable -> {
                    ForecastWidgetContent(
                        context,
                        null
                    )
                }
            }
        }
    }
}

@Composable
fun ForecastWidgetContent(context: Context, forecastAqi: List<Int>?) {
    val backgroundImage: Int
    val pillColor: Color
    val today = LocalDate.now()
    val currentAqi = forecastAqi?.firstOrNull()

    if (currentAqi == null || currentAqi < 50) {
        backgroundImage = R.drawable.good_aqi_forecast_background
        pillColor = Color(0xFF00361C)
    } else {
        backgroundImage = R.drawable.bad_aqi_forecast_background
        pillColor = Color(0xFF362300)
    }

    fun getAqiCategory(aqi: Int?): String {
        return when (aqi) {
            null -> "-"
            in 0..49 -> "Good"
            in 50..99 -> "Fair"
            in 100..149 -> "Unhealthy for Sensitive Groups"
            in 150..199 -> "Unhealthy"
            in 200..299 -> "Very Unhealthy"
            else -> "Hazardous"
        }
    }

    val currentAqiColor = when (currentAqi) {
        null -> Color(0xFF00361C)
        in 0..49 -> Color(0xFF00361C) // Green
        in 50..99 -> Color(0xFFB87801) // Yellow
        in 100..149 -> Color(0xFFB95600) // Orange
        in 150..199 -> Color(0xFFB10909) // Red
        in 200..299 -> Color(0xFF5E0063) // Purple
        else -> Color(0xA39E9E) // Gray
    }

    fun getAqiColor(aqi: Int?): Color {
        return when (aqi) {
            null -> Color(0xFF00361C)
            in 0..49 -> Color(0xFF00361C)
            in 50..99 -> Color(0xFFB87801)
            in 100..149 -> Color(0xFFB95600)
            in 150..199 -> Color(0xFFB10909)
            in 200..299 -> Color(0xFF5E0063)
            else -> Color(0xA39E9E)
        }
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
                        fontFamily = "fonts/sf_pro_rounded_heavy.ttf",
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
                        fontFamily = "fonts/sf_pro_regular.ttf",
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
                        fontFamily = "fonts/sf_pro_rounded_heavy.ttf",
                    )
                )
            )

            Spacer( modifier = GlanceModifier.height(4.dp) )

            Image(
                contentDescription = null,
                provider = ImageProvider(
                    createCustomTextBitmap(
                        context = context,
                        text = getAqiCategory(currentAqi),
                        textColor = aqiColor,
                        fontSize = 12f,
                        fontFamily = "fonts/sf_pro_regular.ttf",
                    )
                )
            )
        }
    }

    Box(
        modifier = GlanceModifier.fillMaxSize()
            .clickable(
                actionStartActivity<AQIForecastActivity>()
            )
    ) {

        Image(
            provider = ImageProvider(backgroundImage),
            contentDescription = null,
            modifier = GlanceModifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(vertical = 12.dp, horizontal = 12.dp)
        ) {
            Image(
                contentDescription = null,
                provider = ImageProvider(
                    createPillTextBitmap(
                        context = context,
                        text = "AQI Forecast in General Santos City",
                        textColor = Color.White,
                        backgroundColor = pillColor,
                        fontSize = 12f,
                        fontFamily = "fonts/sf_pro_regular.ttf",
                        horizontalPadding = 8,
                        verticalPadding = 4,
                        cornerRadius = 32f,
                    )
                )
            )

            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 24.dp)
            ) {
                (forecastAqi ?: emptyList()).take(6).forEachIndexed { index, aqi ->
                    val date = today.plusDays(index.toLong())

                    Box(
                        modifier = GlanceModifier
                            .defaultWeight()
                            .padding(horizontal = 1.dp)
                    ) {
                        row_items(
                            date.dayOfWeek.name.take(3),
                            date.format(DateTimeFormatter.ofPattern("MMM d")),
                            aqi,
                            getAqiCategory(aqi),
                            getAqiColor(aqi)
                        )
                    }
                }
            }
        }
    }
}