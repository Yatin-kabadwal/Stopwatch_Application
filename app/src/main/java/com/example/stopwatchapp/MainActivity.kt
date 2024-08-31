package com.example.stopwatchapp

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textViewTimer: TextView
    private lateinit var buttonStart: Button
    private lateinit var buttonPause: Button
    private lateinit var buttonReset: Button

    private var isRunning = false
    private var elapsedTime = 0L
    private var startTime = 0L

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewTimer = findViewById(R.id.textViewTimer)
        buttonStart = findViewById(R.id.buttonStart)
        buttonPause = findViewById(R.id.buttonPause)
        buttonReset = findViewById(R.id.buttonReset)

        buttonStart.setOnClickListener {
            startTimer()
        }

        buttonPause.setOnClickListener {
            pauseTimer()
        }

        buttonReset.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime
            handler.postDelayed(updateTimerRunnable, 0)
            isRunning = true
        }
    }

    private fun pauseTimer() {
        if (isRunning) {
            handler.removeCallbacks(updateTimerRunnable)
            elapsedTime = System.currentTimeMillis() - startTime
            isRunning = false
        }
    }

    private fun resetTimer() {
        handler.removeCallbacks(updateTimerRunnable)
        elapsedTime = 0L
        textViewTimer.text = "00:00:00"
        isRunning = false
    }

    private val updateTimerRunnable = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - startTime
            val seconds = (elapsedTime / 1000) % 60
            val minutes = (elapsedTime / (1000 * 60)) % 60
            val hours = (elapsedTime / (1000 * 60 * 60)) % 24

            textViewTimer.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)

            handler.postDelayed(this, 1000)
        }
    }
}
