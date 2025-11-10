package com.example.flags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


object HotelFlag: ICSFlag("hotel") {
    override val message = "J’ai un pilote à bord"

    @Composable
    override fun Flag(modifier: Modifier) {
        Row(modifier) {
            Box(Modifier.fillMaxHeight().weight(1f).background(Color.White))
            Box(Modifier.fillMaxHeight().weight(1f).background(Color.Red))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HotelFlagFlagPreview() {
    HotelFlag.Flag(modifier = Modifier.fillMaxHeight())
}
