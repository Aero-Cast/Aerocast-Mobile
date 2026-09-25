package com.aerocast.widgetapp.activity

import android.os.Bundle
import com.aerocast.widgetapp.R
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AQIForecastActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AQIForecastScreen()
        }
    }
}

val SFpro = FontFamily(
    Font(R.font.sf_pro_regular, FontWeight.Normal),
    Font(R.font.sf_pro_semi_bold, FontWeight.SemiBold),
    Font(R.font.sf_pro_rounded_bold, FontWeight.Bold),
    Font(R.font.sf_pro_rounded_heavy, FontWeight.Black)
)

data class FollowingDayData(
    val score: Int,
    val date: String,
    val comparisonText: String
)

data class DailyAqiData(
    val score: Int,
    val dayNumber: Int,
    val dayOfWeek: String
)

@Composable
fun AQIForecastScreen() {
    val pageBackground = Color(0xFFFAF6EE)
    val darkBrownText = Color(0xFF322100) 

    val tomorrowAqi = 52;

    val aqiChartData = listOf(
        DailyAqiData(47, 24, "THU"),
        DailyAqiData(52, 25, "FRI"),
        DailyAqiData(52, 26, "SAT"),
        DailyAqiData(51, 27, "SUN"),
        DailyAqiData(51, 28, "MON"),
        DailyAqiData(51, 29, "TUE")
    )

    val followingDays = listOf(
        FollowingDayData(
            score = 52,
            date = "SEPTEMBER 26",
            comparisonText = "A 0 difference since tomorrow."
        ),
        FollowingDayData(
            score = 51,
            date = "SEPTEMBER 27",
            comparisonText = "A 1 difference since September 26."
        ),
        FollowingDayData(
            score = 51,
            date = "SEPTEMBER 28",
            comparisonText = "A 0 difference since September 27."
        ),
        FollowingDayData(
            score = 51,
            date = "SEPTEMBER 29",
            comparisonText = "A 0 difference since September 28."
        )
    )

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val topicFontSize = when {
        screenWidth < 360 -> 20.sp
        screenWidth < 400 -> 28.sp
        screenWidth < 450 -> 30.sp
        else -> 38.sp
    }

    val dayAheadFontSize = when {
        screenWidth < 360 -> 12.sp
        screenWidth < 400 -> 13.sp
        screenWidth < 450 -> 16.sp
        else -> 18.sp
    }

    val imageSize = when {
        screenWidth < 360 -> 120.dp
        screenWidth < 400 -> 140.dp
        screenWidth < 450 -> 150.dp
        else -> 170.dp
    }

    val dayAheadLineHeight = when {
        screenWidth < 360 -> 16.sp
        screenWidth < 400 -> 18.sp
        screenWidth < 450 -> 20.sp
        else -> 22.sp
    }

    val tomorrowAqiFontSize = when {
        screenWidth < 360 -> 70.sp
        screenWidth < 400 -> 80.sp
        screenWidth < 450 -> 90.sp
        else -> 100.sp
    }

    val tomorrowCategoryFontSize = when {
        screenWidth < 360 -> 12.sp
        screenWidth < 400 -> 14.sp
        screenWidth < 450 -> 16.sp
        else -> 18.sp
    }

    val statusText = when (tomorrowAqi) {
        in 0..50 -> "Good"
        in 51..100 -> "Fair"
        in 101..150 -> "Unhealthy for Sensitive Groups"
        in 151..200 -> "Unhealthy"
        in 201..300 -> "Very Unhealthy"
        else -> "Hazardous"
    }

    val tomorrowBackground = if (tomorrowAqi < 50) R.drawable.good_aqi_current_activity_background else R.drawable.bad_aqi_current_activity_background

    val pillBackground = if (tomorrowAqi < 50) Color(0xFF00361C) else Color(0xFF322100)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = pageBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            AqiChartSection(dataList = aqiChartData)

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Day Ahead!",
                fontSize = topicFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = SFpro
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(imageSize)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    Image(
                        painter = painterResource(tomorrowBackground),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = tomorrowAqi.toString(),
                            fontSize = tomorrowAqiFontSize,
                            fontWeight = FontWeight.Black,
                            fontFamily = SFpro,
                            color = pillBackground,
                            lineHeight = 70.sp
                        )

                        Box(
                            modifier = Modifier
                                .offset(y = (-8).dp)
                                .clip(RoundedCornerShape(50)) 
                                .background(pillBackground)
                                .padding(horizontal = 14.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = statusText,
                                fontSize = tomorrowCategoryFontSize,
                                fontWeight = FontWeight.Normal,
                                fontFamily = SFpro,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                val dayAheadText = buildAnnotatedString {
                    append("Tomorrow's AQI will be ")

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(statusText)
                    }

                    when (tomorrowAqi) {
                        in 0..50 -> {
                            append(". Air quality is ideal for outdoor activities! It’s a great day to get outside, run your errands, or enjoy a walk in the park without any health concerns.")
                        }
                        in 51..100 -> {
                            append(". It's not perfect, but it's a good window to get your errands done before the haze settles back in. If you've been putting off a trip to the market or need to get some outdoor chores finished, the morning hours are your best bet.")
                        }
                        in 101..150 -> {
                            append(". If you have asthma, allergies, or other respiratory conditions, you may want to limit prolonged outdoor exertion. Consider scheduling outdoor errands during off-peak hours or wearing a light mask.")
                        }
                        in 151..200 -> {
                            append(". Everyone may begin to feel health effects, and sensitive groups may experience more serious symptoms. It's best to keep windows closed, avoid heavy outdoor workouts, and stay indoors when possible.")
                        }
                        in 201..300 -> {
                            append(". Air quality is significantly degraded, posing health risks for everyone. Avoid outdoor exertion, run your air purifier indoors, and wear a well-fitted mask (like an N95) if you must step outside.")
                        }
                        else -> {
                            append(". Emergency health warning: the air quality is extremely dangerous. Stay indoors, keep air filtration running, and avoid all unnecessary outdoor travel or physical exertion.")
                        }
                    }
                }

                Text(
                    text = dayAheadText,
                    fontSize = dayAheadFontSize,
                    lineHeight = dayAheadLineHeight,
                    color = Color(0xFF222222),
                    fontFamily = SFpro,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Following Days",
                fontSize = topicFontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = SFpro,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                followingDays.forEach { item ->
                    FollowingDayItem(data = item)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun FollowingDayItem(data: FollowingDayData) {
    val GoodGreen = Color(0xFF038B44)
    val ModerateOrange = Color(0xFFD3B301)

    val badgeBackgroundColor = if (data.score < 50) GoodGreen else ModerateOrange

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val circleSize = when {
        screenWidth < 360 -> 20.dp
        screenWidth < 400 -> 46.dp
        screenWidth < 450 -> 50.dp
        else -> 54.dp
    }

    val scoreFontSize = when {
        screenWidth < 360 -> 16.sp
        screenWidth < 400 -> 20.sp
        screenWidth < 450 -> 24.sp
        else -> 28.sp
    }

    val statusFontSize = when {
        screenWidth < 360 -> 14.sp
        screenWidth < 400 -> 18.sp
        screenWidth < 450 -> 22.sp
        else -> 28.sp
    }

    val dateFontSize = when {
        screenWidth < 360 -> 10.sp
        screenWidth < 400 -> 12.sp
        screenWidth < 450 -> 14.sp
        else -> 16.sp
    }

    val comparisonFontSize = when {
        screenWidth < 360 -> 10.sp
        screenWidth < 400 -> 13.sp
        screenWidth < 450 -> 14.sp
        else -> 18.sp
    }

    val statusText = when (data.score) {
        in 0..50 -> "Good"
        in 51..100 -> "Fair"
        in 101..150 -> "Unhealthy for Sensitive Groups"
        in 151..200 -> "Unhealthy"
        in 201..300 -> "Very Unhealthy"
        else -> "Hazardous"
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(circleSize)
                .clip(CircleShape)
                .background(badgeBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = data.score.toString(),
                color = Color.White,
                fontSize = scoreFontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = SFpro
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = statusText,
                    fontSize = statusFontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontFamily = SFpro
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = data.date,
                    fontSize = dateFontSize,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    fontFamily = SFpro,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = data.comparisonText,
                fontSize = comparisonFontSize,
                fontWeight = FontWeight.Normal,
                fontFamily = SFpro,
                color = Color.Black
            )
        }
    }
}

@Composable
fun AqiChartSection(dataList: List<DailyAqiData>) {

    val LabelGrey = Color(0xFF7F8C9D)
    val GoodGreen = Color(0xFF038B44)
    val ModerateOrange = Color(0xFFD3B301)

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val topicFontSize = when {
        screenWidth < 360 -> 20.sp
        screenWidth < 400 -> 28.sp
        screenWidth < 450 -> 30.sp
        else -> 38.sp
    }

    val subtopicFontSize = when {
        screenWidth < 360 -> 12.sp
        screenWidth < 400 -> 14.sp
        screenWidth < 450 -> 16.sp
        else -> 18.sp
    }

    val chartHeight = when {
        screenWidth < 360 -> 190.dp
        screenWidth < 400 -> 220.dp
        screenWidth < 450 -> 250.dp
        else -> 270.dp
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Chart Header
        Text(
            text = "AQI Forecast",
            color = Color.Black,
            fontSize = topicFontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = SFpro
        )
        Text(
            text = "Daily Overview • September 2026",
            color = LabelGrey,
            fontSize = subtopicFontSize,
            fontWeight = FontWeight.Normal,
            fontFamily = SFpro
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Chart Columns Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartHeight),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dataList.forEach { dailyData ->
                val chartColor = if (dailyData.score < 50) GoodGreen else ModerateOrange

                AqiChartColumn(
                    data = dailyData,
                    color = chartColor
                )
            }
        }
    }
}

@Composable
fun AqiChartColumn(
    data: DailyAqiData,
    color: Color,
    maxAqiScale: Float = 150f
) {

    val ChartGrey = Color(0xFFE0E6EB)
    val LabelGrey = Color(0xFF7F8C9D)
   
    val minHeightFraction = 0.20f
    val normalizedFraction = (data.score.toFloat() / maxAqiScale).coerceIn(0f, 1f)
    val heightFraction = minHeightFraction + (normalizedFraction * (1f - minHeightFraction))

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val scoreFontSize = when {
        screenWidth < 360 -> 12.sp
        screenWidth < 400 -> 14.sp
        screenWidth < 450 -> 16.sp
        else -> 18.sp
    }

    val dayFontSize = when {
        screenWidth < 360 -> 12.sp
        screenWidth < 400 -> 14.sp
        screenWidth < 450 -> 16.sp
        else -> 18.sp
    }

    val weekdayFontSize = when {
        screenWidth < 360 -> 10.sp
        screenWidth < 400 -> 12.sp
        screenWidth < 450 -> 14.sp
        else -> 16.sp
    }

    val columnWidth = when {
        screenWidth < 360 -> 36.dp
        screenWidth < 400 -> 48.dp
        screenWidth < 450 -> 52.dp
        else -> 58.dp
    }

    Column(
        modifier = Modifier.width(columnWidth),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Grey background oval track
        Box(
            modifier = Modifier
                .width(columnWidth)
                .weight(1f)
                .background(ChartGrey, RoundedCornerShape(100.dp))
                .padding(4.dp), // Inset padding so the pill stays nicely inside the track
            contentAlignment = Alignment.BottomCenter
        ) {
            // Dynamic Height Bar Pill
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(heightFraction) // Direct height scaling based on AQI
                    .background(color, RoundedCornerShape(100.dp))
                    .padding(top = 10.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = data.score.toString(),
                    color = Color.White,
                    fontSize = scoreFontSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = SFpro,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Date Labels
        Text(
            text = data.dayNumber.toString(),
            color = Color.Black,
            fontSize = dayFontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = SFpro
        )
        Text(
            text = data.dayOfWeek,
            color = LabelGrey,
            fontSize = weekdayFontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = SFpro
        )
    }
}