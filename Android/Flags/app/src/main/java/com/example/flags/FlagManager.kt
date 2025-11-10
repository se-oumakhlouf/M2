package com.example.flags

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FlagManager(modifier: Modifier = Modifier) {
    var selectedFlag by remember { mutableStateOf<Char?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        FlagGrid(
            letters = ('A'..'Z').toList(),
            selectedFlag = selectedFlag,
            onClick = { clickedLetter ->
                selectedFlag = if (selectedFlag == clickedLetter) null else clickedLetter
            },
            modifier = Modifier.weight(1f)
        )

        val currentFlag = selectedFlag
        if (currentFlag != null) {
            FlagInfoBox(flagChar = currentFlag)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlagManagerPreview() {
    FlagManager()
}
