package com.example.puzzle

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@Composable
fun ConnectedJigsaw(
    puzzleId: String,
    difficulty: Int,
    modifier: Modifier = Modifier,
    onResult: (Boolean) -> Unit
) {
    // État pour stocker la permutation à soumettre
    var submittedPermutation by remember { mutableStateOf<IntArray?>(null) }

    // NOTION : LaunchedEffect pour la validation réseau
    LaunchedEffect(submittedPermutation) {
        submittedPermutation?.let { perm ->
            // Transformation du tableau en chaîne "0,3,2,1..."
            val permString = perm.joinToString(",")
            val url = "https://jigsaw.plade.org/puzzle/$puzzleId/submit?permutation=$permString"

            try {
                val result = withContext(Dispatchers.IO) {
                    val connection = URL(url).openConnection() as java.net.HttpURLConnection
                    if (connection.responseCode == 200) {
                        connection.inputStream.bufferedReader().readText().trim()
                    } else {
                        "KO"
                    }
                }

                // NOTION : Communication vers le parent
                if (result == "OK") {
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            } finally {
                // Réinitialisation pour permettre une nouvelle soumission si besoin
                submittedPermutation = null
            }
        }
    }

    // Affichage du JigsawManager sur tout l'espace
    JigsawManager(
        puzzleId = puzzleId,
        difficulty = difficulty,
        modifier = modifier,
        onSubmit = { finalPermutation ->
            // On déclenche l'effet de bord en mettant à jour l'état
            submittedPermutation = finalPermutation
        }
    )
}