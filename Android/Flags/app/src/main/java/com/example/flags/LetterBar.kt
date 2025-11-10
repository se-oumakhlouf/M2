package com.example.flags

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

@Composable
fun LetterBar(
    letters: List<Char>,
    nbRows: Int,
    selectedLetter: Char?,
    onClick: (Char) -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.Gray
) {
    if (letters.isEmpty()) return

    val itemsPerRow = ceil(letters.size.toFloat() / nbRows).toInt().coerceAtLeast(1)
    val alphabetRows = letters.chunked(itemsPerRow)

    Column(modifier = modifier) {
        for (rowLetters in alphabetRows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (letter in rowLetters) {
                    val isSelected = letter == selectedLetter
                    var textModifier = Modifier
                        .clickable { onClick(letter) }
                        .padding(4.dp)

                    if (isSelected) {
                        textModifier = textModifier.border(1.dp, borderColor)
                    }

                    Text(
                        text = letter.toString(),
                        modifier = textModifier
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LetterBarPreview() {
    var selectedLetter by remember { mutableStateOf<Char?>('C') }

    LetterBar(
        letters = ('A'..'Z').toList(),
        nbRows = 2,
        selectedLetter = selectedLetter,
        onClick = { letter ->
            selectedLetter = if (selectedLetter == letter) null else letter
        }
    )
}
