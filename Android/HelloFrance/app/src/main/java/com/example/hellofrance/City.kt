package com.example.hellofrance

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

data class City(val name: String, val latitude: Float, val longitude: Float, val population: Long) {

    companion object {
        fun loadCities(context: Context, resource: Int): List<City> {
            val cities = mutableListOf<City>()

            val inputStream = context.resources.openRawResource(resource)
            val reader = BufferedReader(InputStreamReader(inputStream))

            reader.forEachLine { line ->
                val parts = line.split('\t')

                if (parts.size == 3) {
                    try {
                        val name = parts[0]

                        val coords = parts[1].split(',')
                        val latitude = coords[0].toFloat()
                        val longitude = coords[1].toFloat()

                        val population = parts[2].toLong()

                        if (population > 25_000) {
                            cities.add(City(name, latitude, longitude, population))
                        }

                    } catch (e: Exception) {
                        Log.e(this.javaClass.name, "Error parsing line: $line")
                    }
                }
            }
            return cities
        }
    }
}
