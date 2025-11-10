package com.example.flags

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun FlagInfoBox(flagChar: Char, modifier: Modifier = Modifier) {
    val flag = ICSFlag.findFlag(flagChar)

    if (flag != null) {
        Row(
            modifier = modifier.height(IntrinsicSize.Max)
        ) {
            flag.Flag(
                Modifier
                    .weight(0.2f)
                    .fillMaxHeight()
            )
            Column(modifier = Modifier.weight(0.8f)) {
                Text(
                    text = flag.codeWord,
                    fontSize = 40.sp
                )
                Text(
                    text = flag.message
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlagInfoBoxPreview() {
    FlagInfoBox(flagChar = 'D')
}
