package com.example.puzzle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun JigsawManager(
    puzzleId: String,
    difficulty: Int,
    modifier: Modifier = Modifier,
    onSubmit: (IntArray) -> Unit
) {
    // NOTION : État de la permutation
    var currentPermutation by remember {
        mutableStateOf(IntArray(difficulty * difficulty) { it })
    }

    // États pour gérer les sélections successives
    var firstPos by remember { mutableStateOf<Int?>(null) }
    var secondPos by remember { mutableStateOf<Int?>(null) }

    // NOTION : LaunchedEffect pour gérer le délai de 500ms
    LaunchedEffect(secondPos) {
        val p1 = firstPos
        val p2 = secondPos
        if (p1 != null && p2 != null) {
            delay(500) // Attente demandée

            // Échange des pièces dans le tableau
            val newPerm = currentPermutation.copyOf()
            val temp = newPerm[p1]
            newPerm[p1] = newPerm[p2]
            newPerm[p2] = temp

            currentPermutation = newPerm

            // Réinitialisation des bordures
            firstPos = null
            secondPos = null
        }
    }

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Jigsaw(
            puzzleId = puzzleId,
            permutation = currentPermutation,
            difficulty = difficulty,
            firstSelected = firstPos,
            secondSelected = secondPos,
            modifier = Modifier.weight(1f),
            onClick = { clickedPos ->
                // Logique de sélection séquentielle
                if (firstPos == null) {
                    firstPos = clickedPos
                } else if (secondPos == null && clickedPos != firstPos) {
                    secondPos = clickedPos
                }
            }
        )

        // Bouton de validation
        Button(
            onClick = { onSubmit(currentPermutation) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Submit")
        }
    }
}