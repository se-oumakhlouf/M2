package com.example.puzzle

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@Composable
fun PuzzleFetcher(
    initialEmail: String = "",
    modifier: Modifier = Modifier,
    onNewPuzzle: (Puzzle) -> Unit
) {

    var email by remember {mutableStateOf(initialEmail)}
    var difficulty by remember {mutableStateOf(4f)}
    var status by remember {mutableStateOf("")}
    var isFetching by remember {mutableStateOf(false)}

    val isValidEmail = email.endsWith("@univ-eiffel.fr") || email.endsWith("@edu.univ-eiffel.fr")

    // On lance l'effet que si isFetching passe à true
    LaunchedEffect(isFetching) {
        if (isFetching) {
            status = "fetching puzzle"
            try {
                // Exécution de l'appel réseau sur un thread IO (indispensable sur Android)
                // Ajouté la ligne suivante dans le manifest (AndroidManifest.xml) pour avoir accès à internet
                // <uses-permission android:name="android.permission.INTERNET" />
                val result = withContext(Dispatchers.IO) {
                    val url = "https://jigsaw.plade.org/puzzle/new?email=$email&difficulty=${difficulty.toInt()}"
                    val connection = URL(url).openConnection() as java.net.HttpURLConnection

                    if (connection.responseCode == 200) {
                        val text = connection.inputStream.bufferedReader().readText()
                        val lines = text.lines()
                        Puzzle(lines[0], lines[1], email, difficulty.toInt())
                    } else {
                        val errorMsg = connection.errorStream?.bufferedReader()?.readText() ?: "Error"
                        throw Exception("Code ${connection.responseCode}: $errorMsg")
                    }
                }
                status = "puzzle fetched"
                onNewPuzzle(result) // information au parent
            } catch (e: Exception) {
                status = "Error: ${e.message}"
            } finally {
                isFetching = false
            }
        }
    }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // TextField avec état d'erreur
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Universitaire") },
            isError = !isValidEmail && email.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        )

        // Slider de difficulté
        Text("Difficulté : ${difficulty.toInt()}")
        Slider(
            value = difficulty,
            onValueChange = { difficulty = it },
            valueRange = 2f..7f,
            steps = 4
        )

        // Bouton pour lancer la recherche
        Button(
            onClick = { isFetching = true },
            enabled = isValidEmail && !isFetching,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Fetch Puzzle")
        }

        Text(text = status, color = if (status.contains("Error")) Color.Red else Color.Green)
    }

}

@Preview
@Composable
fun PuzzleFetcherPreview() {

    val context = LocalContext.current

    PuzzleFetcher(onNewPuzzle = {
        Toast.makeText(context, "New Puzzle Fetched", Toast.LENGTH_SHORT).show()
    })
}