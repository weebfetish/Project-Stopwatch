package com.example.learntobuildatimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Chronometer
import android.widget.ListView
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import android.widget.BaseAdapter



class MainActivity : AppCompatActivity() {
    private lateinit var chronometer: Chronometer
    private lateinit var runButton: MaterialButton
    private lateinit var lapButton: Button
    private lateinit var lapListView: ListView
    private var isRunning = false
    private var startTime: Long = 0L
    private val laps = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        chronometer = findViewById(R.id.chronometer2)
        runButton = findViewById(R.id.materialButton)
        lapButton = findViewById(R.id.button)
        lapListView = findViewById(R.id.listView)

        // Set up lap list view
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, laps)
        lapListView.adapter = adapter

        // Button click listeners
        runButton.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        lapButton.setOnClickListener {
            recordLap()
        }
    }

    private fun startTimer() {
        startTime = SystemClock.elapsedRealtime()
        chronometer.base = startTime
        chronometer.start()
        runButton.text = "STOP"
        lapButton.isEnabled = true
        isRunning = true
    }

    private fun pauseTimer() {
        chronometer.stop()
        runButton.text = "RUN"
        lapButton.isEnabled = false
        isRunning = false
    }

    private fun recordLap() {
        val elapsedTime = SystemClock.elapsedRealtime() - startTime
        val lapTime = formatTime(elapsedTime)
        laps.add(lapTime)
        lapListView.isVisible = true // Show lap list
        (lapListView.adapter as BaseAdapter).notifyDataSetChanged()

    }

    private fun formatTime(time: Long): String {
        val hours = (time / (1000 * 60 * 60)) % 24
        val minutes = (time / (1000 * 60)) % 60
        val seconds = (time / 1000) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
