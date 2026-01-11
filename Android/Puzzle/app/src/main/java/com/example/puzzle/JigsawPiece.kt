package com.example.puzzle

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@Composable
fun JigsawPiece(
    puzzleId: String,
    pieceRank: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var isError by remember { mutableStateOf(false) }

    val url = "https://jigsaw.plade.org/puzzle/$puzzleId/$pieceRank"

    LaunchedEffect(url) {
        try {
            isError = false
            val bitmap = withContext(Dispatchers.IO) {
                val inputStream = URL(url).openStream()
                BitmapFactory.decodeStream(inputStream)
            }
            if (bitmap != null) {
                imageBitmap = bitmap.asImageBitmap()
            } else {
                isError = true
            }
        } catch (e: Exception) {
            isError = true
        }
    }



    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        when {
            isError -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    Text("KO", color = Color.White)
                }
            }

            imageBitmap != null -> {
                Image(
                    bitmap = imageBitmap!!,
                    contentDescription = "Pièce $pieceRank",
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                Box(
                    Modifier.fillMaxSize()
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("...")
                }
            }

        }
    }

}