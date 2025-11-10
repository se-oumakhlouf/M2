package com.example.flags

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


object DeltaFlag: ICSFlag("delta") {
    override val message = "Ne me gênez pas, je manœuvre avec difficulté"

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f).background(Color.Yellow))
            Box(Modifier.fillMaxWidth().weight(2f).background(Color.Blue))
            Box(Modifier.fillMaxWidth().weight(1f).background(Color.Yellow))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeltaFlagPreview() {
    DeltaFlag.Flag(modifier = Modifier.fillMaxWidth())
}
