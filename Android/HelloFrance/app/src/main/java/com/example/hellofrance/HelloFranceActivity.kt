package com.example.hellofrance

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HelloFranceActivity : AppCompatActivity() {

    private val minLat = 41.366036f
    private val maxLat = 51.0898f
    private val minLon = -5.143109f
    private val maxLon = 9.55316f
    private val MAX_TRIES = 10
    private val WIN_DISTANCE_KM = 50

    private lateinit var franceMap: ImageView
    private lateinit var cityToFindView: TextView
    private lateinit var resultView: TextView

    private lateinit var cities: List<City>
    private var currentCity: City? = null
    private var tries = 0

    private val colors = listOf(
        Color.parseColor("#FFFFFF"),
        Color.parseColor("#FFEB3B"),
        Color.parseColor("#FFC107"),
        Color.parseColor("#FF9800"),
        Color.parseColor("#FF5722"),
        Color.parseColor("#F44336"),
        Color.parseColor("#E91E63"),
        Color.parseColor("#9C27B0"),
        Color.parseColor("#673AB7"),
        Color.parseColor("#3F51B5"),
        Color.parseColor("#212121")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hello_france)
        setupWindowInsets()

        franceMap = findViewById(R.id.franceMap)
        cityToFindView = findViewById(R.id.topTextView)
        resultView = findViewById(R.id.downTextView)

        cities = City.loadCities(this, R.raw.cities)
        if (cities.isEmpty()) {
            cityToFindView.text = "Erreur: Aucune ville chargée"
            return
        }

        startNewRound()

        setupQuitButton()
        setupMapTouchListener()
    }

    private fun startNewRound() {
        tries = 0
        currentCity = cities.random()
        cityToFindView.text = "Où se trouve ${currentCity?.name} ?"
        updateResultView("Faites votre premier essai !", tries)
    }

    private fun handleTouch(event: MotionEvent) {
        val city = currentCity ?: return

        val clickedLon = minLon + (event.x / franceMap.width) * (maxLon - minLon)
        val clickedLat = maxLat - (event.y / franceMap.height) * (maxLat - minLat)

        val results = FloatArray(1)
        Location.distanceBetween(
            city.latitude.toDouble(), city.longitude.toDouble(),
            clickedLat.toDouble(), clickedLon.toDouble(),
            results
        )
        val distanceInKm = results[0] / 1000

        if (distanceInKm < WIN_DISTANCE_KM) {
            Toast.makeText(this, "Bravo !", Toast.LENGTH_SHORT).show()
            startNewRound()
        } else {
            tries++
            if (tries >= MAX_TRIES) {
                updateResultView("Perdu ! La ville était ${city.name}. Nouvelle partie.", tries)
                startNewRound()
            } else {
                val message = "Raté ! À ${String.format("%.0f", distanceInKm)} km. Essai ${tries}/${MAX_TRIES}"
                updateResultView(message, tries)
            }
        }
    }

    private fun updateResultView(message: String, currentTry: Int) {
        resultView.text = message
        resultView.setBackgroundColor(colors.getOrElse(currentTry) { colors.last() })
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupQuitButton() {
        findViewById<Button>(R.id.quitButton).setOnClickListener {
            Toast.makeText(this, "À bientôt !", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupMapTouchListener() {
        franceMap.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                handleTouch(event)
            }
            true
        }
    }
}
