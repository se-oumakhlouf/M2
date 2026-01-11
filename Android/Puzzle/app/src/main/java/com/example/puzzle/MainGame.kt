package com.example.puzzle

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainGame(modifier: Modifier = Modifier) {
    // NOTION : État pour gérer la navigation entre les écrans
    var currentStep by remember { mutableStateOf("splash") }

    // Données partagées entre les étapes
    var currentPuzzle by remember { mutableStateOf<Puzzle?>(null) }
    var attempts by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    // NOTION : Branchement conditionnel pour l'affichage
    when (currentStep) {
        "splash" -> {
            SplashScreen(modifier = modifier, onClick = { currentStep = "fetcher" })
        }

        "fetcher" -> {
            PuzzleFetcher(
                modifier = modifier,
                initialEmail = currentPuzzle?.email ?: "", // Pré-remplissage si retour
                onNewPuzzle = { puzzle ->
                    currentPuzzle = puzzle
                    attempts = 0 // Reset des essais pour un nouveau puzzle
                    currentStep = "game"
                }
            )
        }

        "game" -> {
            currentPuzzle?.let { puzzle ->
                ConnectedJigsaw(
                    modifier = modifier,
                    puzzleId = puzzle.id,
                    difficulty = puzzle.difficulty,
                    onResult = { isCorrect ->
                        if (isCorrect) {
                            currentStep = "success"
                        } else {
                            attempts++
                            if (attempts >= 3) {
                                currentStep = "failure"
                            } else {
                                Toast.makeText(context, "Faux ! Essai $attempts/3", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            }
        }

        "success", "failure" -> {
            EndScreen(
                isSuccess = (currentStep == "success"),
                onRestart = { currentStep = "fetcher" }
            )
        }
    }
}