package com.energykhata.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.energykhata.R

@Composable
fun MeterGridItem(
    meterName: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(15.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.card_meter),
                contentDescription = "Meter Icon",
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .padding(5.dp)
            )

            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = meterName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            // Title and Units Consumed
//                Column(
//                    modifier = Modifier.weight(1f) // Take up the remaining space
//                ) {
//                    Text(
//                        text = meterName,
//                        style = MaterialTheme.typography.labelSmall,
//                        color = MaterialTheme.colorScheme.onSurface
//                    )
//                Text(
//                    text = "Units Consumed: $unitsConsumed",
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//                )
//                }

            // Arrow Icon on the right
//                Icon(
//                    imageVector = Icons.Default.ArrowForward,
//                    contentDescription = "Go to Details",
//                    tint = MaterialTheme.colorScheme.primary
//                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeterCardPreview() {
    MeterGridItem(
        meterName = "Meter 1",
        onClick = {}
    )
}