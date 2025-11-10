package com.example.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

object LimaFlag : ICSFlag("lima") {
    override val message = "Stoppez votre navire immédiatement"

    @Composable
    override fun Flag(modifier: Modifier) {
        Box(
            modifier = modifier
                .aspectRatio(1f)
        ) {
            Column(Modifier.fillMaxSize()) {
                Row(Modifier.weight(1f)) {
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color.Yellow)
                    )
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color.Black)
                    )
                }
                Row(Modifier.weight(1f)) {
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color.Black)
                    )
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color.Yellow)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LimaFlagPreview() {
    LimaFlag.Flag(modifier = Modifier.fillMaxWidth())
}
