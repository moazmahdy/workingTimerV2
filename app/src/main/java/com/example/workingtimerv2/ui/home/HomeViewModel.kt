package com.example.workingtimerv2.ui.home


import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.workingtimerv2.Navigator
import com.example.workingtimerv2.base.BaseViewModel
import com.example.workingtimerv2.model.AppUser

import kotlinx.coroutines.*
import java.util.*

class HomeViewModel : BaseViewModel<Navigator>() {

    var headerText = ObservableField<String>()
    var timer      = ObservableField<String>()
    var todayDate  = ObservableField<String>()
    private var remainingTime = 7 * 60 * 60 * 1000L
    private var job: Job? = null
    private var isTimerRunning = false
    private val calendar = Calendar.getInstance()


    // Method to start the timer
    fun startTimer() {
        if (!isTimerRunning) {
            job = viewModelScope.launch {
                while (remainingTime > 0) {
                    delay(1000)
                    remainingTime -= 1000
                    updateTimerText()
                }
            }
            isTimerRunning = true
        }
    }

    // Method to make the text timer -- change text to current time
    private suspend fun updateTimerText() {
        val hours = remainingTime / (60 * 60 * 1000) % 24
        val minutes = remainingTime / (60 * 1000) % 60
        val seconds = remainingTime / 1000 % 60
        val timerText = "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
        withContext(Dispatchers.Main) {
            timer.set(timerText)
        }
    }

    // Method to stop the timer
    fun stop() {
        job?.cancel()
    }

    // Method to pause the timer
    // If user clicks on the button again it will continue the timer
    fun pause() {
        if (isTimerRunning) {
            job?.cancel()
            isTimerRunning = false
        } else {
            startTimer()
        }
    }

    fun setDate(){
        todayDate.set("" +calendar.get(Calendar.DAY_OF_MONTH)
                + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" +
                calendar.get(Calendar.YEAR))
    }


    fun setUserName(name: String){
        headerText.set("Hello $name How are you today")
    }
}