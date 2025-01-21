package com.energykhata.ui.screens.calculation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.energykhata.util.scaledFontSize

@Composable
fun SaveReadingToggle(
    isSaveEnabled: Boolean,
    onToggleChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                fontWeight = FontWeight.Bold,
                color = Color(0XFF008D9F),
                text = "Save current reading?",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = scaledFontSize(16f,14f,12f)
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "Turn off if this is a temporary calculation.",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                fontSize = scaledFontSize(12f,11f,10f),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = isSaveEnabled,
            onCheckedChange = onToggleChange,
            colors = SwitchDefaults.colors(
                checkedIconColor = Color(0XFF008D9F),
                checkedThumbColor = Color(0XFF008D9F),
                checkedTrackColor = Color(0XFFE6F8FB),
                checkedBorderColor = Color(0XFF008D9F),
            )
        )
    }
}
