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
import com.aerocast.widgetapp.activity.AQITipsActivity
import android.graphics.Bitmap


class AQITipsWidget : GlanceAppWidget() {

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
            when(state){
                is AQIInfo.Loading -> { TipsWidgetContent( context, null ) }

                is AQIInfo.Available -> {
                    TipsWidgetContent(
                        context,
                        state.currentAqi
                    )
                }

                is AQIInfo.Unavailable -> { TipsWidgetContent( context, null ) }
            }
        }
    }
}

@Composable
fun TipsWidgetContent(context: Context, currentAQI: Int?) {
    val backgroundImage: Int

    if (currentAQI == null || currentAQI < 50) {
        backgroundImage = R.drawable.aqi_tips_background
    } else {
        backgroundImage = R.drawable.aqi_warning_background
    }

    Box(
        modifier = GlanceModifier
        .fillMaxSize()
        .clickable(
            actionStartActivity<AQITipsActivity>()
        )
    ) {

        Image(
            provider = ImageProvider(backgroundImage),
            contentDescription = null,
            modifier = GlanceModifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}