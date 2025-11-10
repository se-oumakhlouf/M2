package com.example.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


object IndiaFlag: ICSFlag("india") {
    override val message = "Je viens sur bâbord."

    @Composable
    override fun Flag(modifier: Modifier) {
        Box(
            modifier = modifier
                .background(Color.Yellow),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .clip(CircleShape)
                    .background(Color.Black)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun IndiaFlagPreview() {
    IndiaFlag.Flag(modifier = Modifier.fillMaxWidth())
}