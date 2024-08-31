package com.example.stopwatchapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button

    private var startTime = 0L
    private var timeInMilliseconds = 0L
    private var handler = Handler(Looper.getMainLooper())
    private var isRunning = false

    private val runnable = object : Runnable {
        override fun run() {
            timeInMilliseconds = System.currentTimeMillis() - startTime
            val seconds = (timeInMilliseconds / 1000).toInt() % 60
            val minutes = (timeInMilliseconds / (1000 * 60)).toInt() % 60
            val milliseconds = (timeInMilliseconds % 1000).toInt() / 10

            val timeString = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
            timerTextView.text = timeString
            handler.postDelayed(this, 50)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        pauseButton = findViewById(R.id.pauseButton)
        resetButton = findViewById(R.id.resetButton)

        startButton.setOnClickListener {
            startTimer()
        }

        pauseButton.setOnClickListener {
            pauseTimer()
        }

        resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - timeInMilliseconds
            handler.post(runnable)
            isRunning = true
        }
    }

    private fun pauseTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable)
            isRunning = false
        }
    }

    private fun resetTimer() {
        handler.removeCallbacks(runnable)
        isRunning = false
        timeInMilliseconds = 0L
        timerTextView.text = "00:00:00"
    }
}
