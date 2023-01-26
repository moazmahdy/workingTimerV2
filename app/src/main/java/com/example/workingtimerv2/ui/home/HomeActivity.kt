package com.example.workingtimerv2.ui.home

import android.content.Context
import kotlinx.coroutines.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workingtimerv2.R

class HomeActivity : AppCompatActivity() {

    private var startTime: Long = 0
    private var endTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Start the timer when the activity is created
        startTime = System.currentTimeMillis()

        // Set an onClickListener for a button that will end the timer and save the time
    }

}