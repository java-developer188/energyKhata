package com.energykhata.ui.screens.calculation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

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
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                color = Color(0XFF00BCD4),
                text = "Save current reading?",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                text = "Turn off if this is a temporary calculation.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = isSaveEnabled,
            onCheckedChange = onToggleChange,
            colors = SwitchDefaults.colors(
                checkedIconColor = Color(0XFF00BCD4),
                checkedThumbColor = Color(0XFF00BCD4),
                checkedTrackColor = Color(0XFFE6F8FB),
                checkedBorderColor = Color(0XFF00BCD4),
//                uncheckedIconColor = Color(0XFF00BCD4),
//                uncheckedThumbColor = Color(0XFF00BCD4),
//                uncheckedTrackColor = Color.White,
//                uncheckedBorderColor = Color(0XFF00BCD4)
            )
        )
    }
}
