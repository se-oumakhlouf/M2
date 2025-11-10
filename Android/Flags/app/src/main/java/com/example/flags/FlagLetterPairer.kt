package com.example.flags

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FlagLetterPairer(
    onPairFound: () -> Unit,
    modifier: Modifier = Modifier
) {
    var remainingLetters by remember { mutableStateOf(('A'..'Z').toList()) }
    var shuffledFlags by remember { mutableStateOf(emptyList<Char>()) }

    var selectedLetter by remember { mutableStateOf<Char?>(null) }
    var selectedFlag by remember { mutableStateOf<Char?>(null) }

    LaunchedEffect(Unit) {
        shuffledFlags = remainingLetters.shuffled()
    }

    LaunchedEffect(selectedLetter, selectedFlag) {
        val currentLetter = selectedLetter
        val currentFlag = selectedFlag

        if (currentLetter != null && currentFlag != null) {
            if (currentLetter == currentFlag) {
                remainingLetters = remainingLetters.filter { it != currentLetter }
                shuffledFlags = shuffledFlags.filter { it != currentFlag }
                onPairFound()
            }
            selectedLetter = null
            selectedFlag = null
        }
    }

    BoxWithConstraints(modifier = modifier) {
        val isPortrait = this.maxHeight > this.maxWidth
        val letterBarRows = if (isPortrait) 2 else 1

        Column(Modifier.fillMaxSize()) {
            LetterBar(
                letters = remainingLetters,
                nbRows = letterBarRows,
                selectedLetter = selectedLetter,
                onClick = { clickedLetter ->
                    if (selectedFlag != null) {
                        selectedLetter = clickedLetter
                    } else {
                        selectedLetter = if (selectedLetter == clickedLetter) null else clickedLetter
                    }
                },
                borderColor = Color.Red
            )

            FlagGrid(
                letters = shuffledFlags,
                selectedFlag = selectedFlag,
                onClick = { clickedFlag ->
                    if (selectedLetter != null) {
                        selectedFlag = clickedFlag
                    } else {
                        selectedFlag = if (selectedFlag == clickedFlag) null else clickedFlag
                    }
                },
                modifier = Modifier.weight(1f),
                borderColor = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlagLetterPairerPreview() {
    FlagLetterPairer(onPairFound = { })
}
