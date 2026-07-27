package com.aerocast.widgetapp

import androidx.glance.appwidget.GlanceAppWidgetReceiver

class HelloWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget = HelloWidget()

}