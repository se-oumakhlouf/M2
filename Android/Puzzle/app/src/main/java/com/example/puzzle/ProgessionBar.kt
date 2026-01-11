package com.example.puzzle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun FillBar(fillRatio: Float, modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(10f)
            .border(1.dp, Color.Black, RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .weight(fillRatio.coerceAtLeast(0.01f))
                    .fillMaxHeight()
                    .background(Color.Black)
            )

            Box(
                modifier = Modifier
                    .weight((1f - fillRatio).coerceAtLeast(0.01f))
                    .fillMaxHeight()
                    .background(Color.White)
            )
        }

        Text(
            text = "${(fillRatio * 100).toInt()}%",
            color = Color.Gray
        )

    }
}

@Preview
@Composable
fun FillBarPreview() {
    FillBar(fillRatio = 0.2f)
}