package com.aerocast.widgetapp.activity

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest

@Preview(
    name = "Phone Small",
    showBackground = true,
    widthDp = 320,
    heightDp = 640
)
@Composable
@PreviewTest
fun AQITips320x640() = AQIForecastScreen()

@Preview(
    name = "Phone Medium",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
@PreviewTest
fun AQITips360x800() = AQIForecastScreen()

@Preview(
    name = "Phone Large",
    showBackground = true,
    widthDp = 412,
    heightDp = 915
)
@Composable
@PreviewTest
fun AQITips412x915() = AQIForecastScreen()

@Preview(
    name = "My Phone",
    showBackground = true,
    widthDp = 451,
    heightDp = 1002
)
@Composable
@PreviewTest
fun AQITips451x1002() = AQIForecastScreen()