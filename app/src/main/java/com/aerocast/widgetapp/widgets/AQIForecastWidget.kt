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
                        null,
                        null
                    )
                }

                is AQIInfo.Available -> {
                    android.util.Log.d(
                        "AQIForecastWidget",
                        "currentAqi=${state.currentAqi}, forecastAqi=${state.forecastAqi}"
                    )

                    ForecastWidgetContent(
                        context,
                        state.currentAqi,
                        state.forecastAqi
                    )
                }

                is AQIInfo.Unavailable -> {
                    ForecastWidgetContent(
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
fun ForecastWidgetContent(context: Context, currentAqi: Int?, forecastAqi: List<Int>?) {
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
        in 51..100 -> Color(0xFFB87801) // Yellow
        in 101..150 -> Color(0xFFB95600) // Orange
        in 151..200 -> Color(0xFFB10909) // Red
        in 201..300 -> Color(0xFF5E0063) // Purple
        else -> Color(0xA39E9E) // Gray
    }

    fun getAqiColor(aqi: Int?): Color {
        return when (aqi) {
            null -> Color(0xFF00361C)
            in 0..50 -> Color(0xFF00361C)
            in 51..100 -> Color(0xFFB87801)
            in 101..150 -> Color(0xFFB95600)
            in 151..200 -> Color(0xFFB10909)
            in 201..300 -> Color(0xFF5E0063)
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
                        textColor = Color(0xFF00361C),
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
                        textColor = Color(0xFF00361C),
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
                        text = category,
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
                    .padding(top = 12.dp)
                    .wrapContentWidth()
            ) {
                row_items("THU", "Sep 24", currentAqi, category, currentAqiColor)

                row_gap()

                row_items("FRI", "Sep 25", forecastAqi?.getOrNull(0), getAqiCategory(forecastAqi?.getOrNull(0)), getAqiColor(forecastAqi?.getOrNull(0)))

                row_gap()

                row_items("SAT", "Sep 26", forecastAqi?.getOrNull(1), getAqiCategory(forecastAqi?.getOrNull(1)), getAqiColor(forecastAqi?.getOrNull(1)))

                row_gap()

                row_items("SUN", "Sep 27", forecastAqi?.getOrNull(2), getAqiCategory(forecastAqi?.getOrNull(2)), getAqiColor(forecastAqi?.getOrNull(2)))

                row_gap()

                row_items("MON", "Sep 28", forecastAqi?.getOrNull(3), getAqiCategory(forecastAqi?.getOrNull(3)), getAqiColor(forecastAqi?.getOrNull(3)))

                row_gap()

                row_items("TUE", "Sep 29", forecastAqi?.getOrNull(4), getAqiCategory(forecastAqi?.getOrNull(4)), getAqiColor(forecastAqi?.getOrNull(4)))
            }
        }
    }
}