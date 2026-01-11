package com.example.puzzle

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, restartKey: Long = 0L, onClick: () -> Unit) {

    // Variables d'état pour piloter l'UI
    var progress by remember(restartKey) { mutableStateOf(0f) }
    var isFinished by remember(restartKey) { mutableStateOf(false) }

    // LaunchedEffect(Unit)
    LaunchedEffect(restartKey) {
        val duration = 2000L
        val steps = 100
        val delayTime = duration / steps

        for (i in 1..steps) {
            delay(delayTime)
            progress = i / 100f
        }
        isFinished = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        // Sierra
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .aspectRatio(1f)
                    .background(Color.Blue)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp)
                .align(Alignment.BottomCenter)
        ) {
            if (!isFinished) {
                FillBar(
                    fillRatio = progress,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.Center)
                )
            } else {
                Button(onClick = onClick,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text("Start")
                }
            }
        }


    }

}

@Preview
@Composable
fun SplashScreenPreview() {

    // Récupérer le contexte Android dans un Composable
    val context = LocalContext.current

    SplashScreen(onClick = {
        // La syntaxe Android standard : Context, Texte, Durée, .show()
        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
    })
}

@Preview
@Composable
fun RestartableSplashScreen() {
    var keyToRestart by remember { mutableStateOf(0L) }

    SplashScreen(
        restartKey = keyToRestart,
        onClick = {
            // Changer la clé réinitialise le progress et isFinished à leur valeur par défaut
            keyToRestart = System.currentTimeMillis()
        }
    )
}