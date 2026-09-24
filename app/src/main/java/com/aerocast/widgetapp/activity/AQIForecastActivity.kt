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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.*
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

data class FollowingDayData(
    val score: Int,
    val status: String,
    val date: String,
    val comparisonText: String,
    val badgeBackgroundColor: Color
)

@Composable
fun AQIForecastScreen() {
    val pageBackground = Color(0xFFFAF6EE)
    val darkBrownText = Color(0xFF322100) 
    val pillBackground = Color(0xFF322100)

    val SFpro = FontFamily(
        Font(R.font.sf_pro_regular, FontWeight.Normal),
        Font(R.font.sf_pro_semi_bold, FontWeight.SemiBold),
        Font(R.font.sf_pro_rounded_bold, FontWeight.Bold),
        Font(R.font.sf_pro_rounded_heavy, FontWeight.Black)
    )

    val followingDays = listOf(
        FollowingDayData(
            score = 60,
            status = "Fair",
            date = "MARCH 3",
            comparisonText = "A 29 difference since tomorrow.",
            badgeBackgroundColor = Color(0xFF4A320A)
        ),
        FollowingDayData(
            score = 58,
            status = "Good",
            date = "MARCH 4",
            comparisonText = "A 12 difference since March 3.",
            badgeBackgroundColor = Color(0xFF0C4E32)
        ),
        FollowingDayData(
            score = 60,
            status = "Fair",
            date = "MARCH 3",
            comparisonText = "A 29 difference since tomorrow.",
            badgeBackgroundColor = Color(0xFF4A320A)
        ),
        FollowingDayData(
            score = 58,
            status = "Good",
            date = "MARCH 4",
            comparisonText = "A 12 difference since March 3.",
            badgeBackgroundColor = Color(0xFF0C4E32)
        )
    )

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
            Text(
                text = "Forecast",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = SFpro
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFBD28B)), 
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Forecast Graph Image Placeholder",
                    color = Color(0xFF4A320A),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = SFpro
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Day Ahead!",
                fontSize = 28.sp,
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
                        .size(150.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    Image(
                        painter = painterResource(R.drawable.bad_aqi_current_activity_background),
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
                            text = "89",
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = SFpro,
                            color = darkBrownText,
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
                                text = "Fair",
                                fontSize = 13.sp,
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
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append("fair")
                    }
                    append(". It's not perfect, but it's a good window to get your errands done before the haze settles back in. If you've been putting off a trip to the market or need to get some outdoor chores finished, the morning hours are your best bet.")
                }

                Text(
                    text = dayAheadText,
                    fontSize = 13.sp,
                    lineHeight = 17.sp,
                    color = Color(0xFF222222),
                    fontFamily = SFpro,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Following Days",
                fontSize = 28.sp,
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
    val SFpro = FontFamily(
        Font(R.font.sf_pro_regular, FontWeight.Normal),
        Font(R.font.sf_pro_semi_bold, FontWeight.SemiBold),
        Font(R.font.sf_pro_rounded_bold, FontWeight.Bold)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(data.badgeBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = data.score.toString(),
                color = Color.White,
                fontSize = 20.sp,
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
                    text = data.status,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontFamily = SFpro
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = data.date,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    fontFamily = SFpro,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = data.comparisonText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = SFpro,
                color = Color(0xFF444444)
            )
        }
    }
}