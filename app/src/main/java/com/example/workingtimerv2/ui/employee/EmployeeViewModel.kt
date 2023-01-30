package com.example.workingtimerv2.ui.employee


import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.workingtimerv2.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import java.util.*

class EmployeeViewModel : BaseViewModel<Navigator>() {

    var headerText = ObservableField<String>()
    var timer = ObservableField<String>()
    var todayDate = ObservableField<String>()
    var weekHourTV = ObservableField<String>()
    var dayHourTV = ObservableField<String>()
    var monthHourTV = ObservableField<String>()
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
        saveHourTime()
        saveWeekTime()
        saveMonthHour()
    }

    private fun saveHourTime() {
        val hours = remainingTime / (60 * 60 * 1000) % 24
        val total = 7 - hours
        dayHourTV.set(total.toString() + "h")
    }

    private fun saveWeekTime() {
        var counter = 0
        while (counter < 7){
            val hours = remainingTime / (60 * 60 * 1000) % 24
            val totalInDay = 7 - hours
            var totalInWeek: Long = 0
            totalInWeek += totalInDay
            weekHourTV.set(totalInWeek.toString() + "h")
            counter++
        }
    }

    private fun saveMonthHour() {
        var counter = 0
        while (counter < 30){
            val hours = remainingTime / (60 * 60 * 1000) % 24
            val totalInDay = 7 - hours
            var totalInMonth: Long = 0
            totalInMonth += totalInDay
            monthHourTV.set(totalInMonth.toString() + "h")
            counter++
        }
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
        headerText.set("Hello $name \n" +
                "How are you today")
    }
    fun logout(){
        FirebaseAuth.getInstance().signOut();
        navigator?.openLoginScreen()
    }
}