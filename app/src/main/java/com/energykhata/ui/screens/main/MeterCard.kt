package com.energykhata.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.energykhata.R
import com.energykhata.util.scaledFontSize

@Composable
fun MeterGridItem(
    meterName: String
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(15.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.meter_icon),
                contentDescription = "Meter Icon",
                modifier = Modifier
                    .size(
                        when {
                            screenWidth > 600 -> 100.dp // Large screen
                            screenWidth > 360 -> 75.dp // Medium screen
                            else -> 55.dp // Small screen
                        }
                    )
                    .padding(5.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = meterName,
                style = MaterialTheme.typography.titleLarge,
                fontSize = scaledFontSize(22f,18f,14f),
                color = Color(0XFF008D9F),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
                modifier = Modifier.scale(1.0f)
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(name = "Small Device", showBackground = true, device = "spec:width=360dp,height=640dp")
@Preview(name = "Large Device", showBackground = true, device = "spec:width=1080dp,height=1920dp")
@Composable
fun MeterCardPreview() {
    MeterGridItem(
        meterName = "Meter 1234567890 1111111111 2222222222"
    )
}