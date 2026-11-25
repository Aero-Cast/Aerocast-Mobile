package com.aerocast.widgetapp.activity

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest

@Composable
private fun TestForecastScreen() {
    AQITipsScreen()
}

@Preview(
    name = "Phone Small",
    showBackground = true,
    widthDp = 320,
    heightDp = 640
)
@Composable
@PreviewTest
fun AQITips320x640() = TestForecastScreen()

@Preview(
    name = "Phone Medium",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
@PreviewTest
fun AQITips360x800() = TestForecastScreen()

@Preview(
    name = "Phone Large",
    showBackground = true,
    widthDp = 412,
    heightDp = 915
)
@Composable
@PreviewTest
fun AQITips412x915() = TestForecastScreen()

@Preview(
    name = "My Phone",
    showBackground = true,
    widthDp = 451,
    heightDp = 1402
)
@Composable
@PreviewTest
fun AQITips451x1402() = TestForecastScreen()