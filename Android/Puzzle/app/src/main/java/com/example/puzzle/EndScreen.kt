package com.example.puzzle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EndScreen(isSuccess: Boolean, onRestart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isSuccess) "Félicitations ! Vous avez gagné !" else "Dommage... Trop d'essais.",
            style = MaterialTheme.typography.headlineMedium,
            color = if (isSuccess) Color(0xFF388E3C) else Color.Red
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onRestart) {
            Text("Rejouer avec un nouveau puzzle")
        }
    }
}