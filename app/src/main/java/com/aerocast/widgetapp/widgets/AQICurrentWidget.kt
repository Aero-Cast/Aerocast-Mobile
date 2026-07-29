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
import androidx.glance.layout.*
import androidx.glance.text.*
import androidx.glance.unit.ColorProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.aerocast.widgetapp.R
import com.aerocast.widgetapp.utils.createCustomTextBitmap
import com.aerocast.widgetapp.utils.createPillTextBitmap
import android.graphics.Bitmap


class AQICurrentWidget : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            val currentAQI = 89

            MyWidgetContent(context, currentAQI)
        }
    }
}

@Composable
fun MyWidgetContent(context: Context, currentAQI: Int) {
    val backgroundImage: Int
    val themeColor: Color
    val statusText: String

    if (currentAQI >= 50) {
        backgroundImage = R.drawable.bad_aqi_current_widget_background
        themeColor = Color(0xFF362300)
        statusText = "Fair Air Quality"
    } else {
        backgroundImage = R.drawable.good_aqi_current_widget_background
        themeColor = Color(0xFF00361C)
        statusText = "Good Air Quality"
    }

    Box(
        modifier = GlanceModifier.fillMaxSize()
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
                .padding(8.dp)
        ) {
            Image(
                contentDescription = null,
                provider = ImageProvider(
                    createPillTextBitmap(
                        context = context,
                        text = "Today in General Santos",
                        textColor = Color.White,
                        backgroundColor = themeColor,
                        fontSize = 16f,
                        fontFamily = "fonts/SF-Pro-Regular.ttf",
                        horizontalPadding = 8,
                        verticalPadding = 6,
                        cornerRadius = 32f,
                    )
                )
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(
                        top = 40.dp,
                        bottom = 8.dp
                    )
            ) {
                Image(
                    provider = ImageProvider(
                        createCustomTextBitmap(
                            context = context,
                            text = currentAQI.toString(),
                            textColor = themeColor,
                            fontSize = 172f,
                            fontFamily = "fonts/SF-Pro-Rounded-Heavy.ttf"
                        )
                    ),
                    contentDescription = null
                )
            }

            Box (
                contentAlignment = Alignment.Center,
                modifier = GlanceModifier.fillMaxWidth()
            ) { 
                Image(
                    contentDescription = null,
                    provider = ImageProvider(
                        createPillTextBitmap(
                            context = context,
                            text = statusText,
                            textColor = Color.White,
                            backgroundColor = themeColor,
                            fontSize = 24f,
                            fontFamily = "fonts/SF-Pro-Regular.ttf",
                            horizontalPadding = 8,
                            verticalPadding = 6,
                            cornerRadius = 32f,
                        )
                    )
                )
            }
        }
    }
}