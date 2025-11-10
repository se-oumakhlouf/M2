package com.example.flags

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FlagGrid(
    letters: List<Char>,
    selectedFlag: Char?,
    onClick: (Char) -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.Black
) {
    val flagSize = 80.dp

    BoxWithConstraints(modifier = modifier) {
        val flagsPerRow = (this.maxWidth / flagSize).toInt().coerceAtLeast(1)
        val alphabetRows = letters.chunked(flagsPerRow)

        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
        ) {
            items(alphabetRows) { rowLetters ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (letter in rowLetters) {
                        val isSelected = selectedFlag == letter
                        val border = if (isSelected) BorderStroke(2.dp, borderColor) else null
                        val itemModifier = Modifier
                            .size(flagSize)
                            .clickable { onClick(letter) }
                            .then(if (border != null) Modifier.border(border) else Modifier)

                        when (val flag = ICSFlag.findFlag(letter)) {
                            null -> MissingFlag(itemModifier, letter.toString())
                            else -> flag.Flag(itemModifier)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlagGridTester() {
    var letterClicked by remember { mutableStateOf<Char?>(null) }
    FlagGrid(
        letters = ('A'..'Z').toList(),
        selectedFlag = letterClicked, 
        onClick = {
            letterClicked = if (it == letterClicked) null else it
        }
    )
}
