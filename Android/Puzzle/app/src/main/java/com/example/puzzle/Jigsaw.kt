package com.example.puzzle

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Jigsaw(
    puzzleId: String,
    permutation: IntArray,
    difficulty: Int, // N
    modifier: Modifier = Modifier,
    firstSelected: Int? = null,  // Position de la 1ère pièce sélectionnée
    secondSelected: Int? = null, // Position de la 2ème pièce sélectionnée
    onClick: (Int) -> Unit
) {
    // Calcul de l'aspect ratio pour garder une grille carrée
    Column(modifier = modifier.aspectRatio(1f)) {
        repeat(difficulty) { row ->
            Row(modifier = Modifier.weight(1f)) {
                repeat(difficulty) { col ->
                    val position = row * difficulty + col
                    val pieceRank = permutation[position]

                    // Détermination de la bordure selon la sélection
                    val borderColor = when (position) {
                        firstSelected -> Color.Red
                        secondSelected -> Color.Blue
                        else -> Color.Transparent
                    }

                    JigsawPiece(
                        puzzleId = puzzleId,
                        pieceRank = pieceRank,
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                            .border(if (borderColor != Color.Transparent) 4.dp else 0.dp, borderColor),
                        onClick = { onClick(position) }
                    )
                }
            }
        }
    }
}