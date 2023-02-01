package com.example.workingtimerv2.ui.employee


import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.workingtimerv2.R
import com.example.workingtimerv2.base.BaseViewModel
import com.example.workingtimerv2.model.AppUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.*
import java.util.*

class EmployeeViewModel : BaseViewModel<Navigator>() {

    var headerText = ObservableField<String>()
    var timer      = ObservableField<String>()
    var todayDate  = ObservableField<String>()


    var yesterdayWorkedTimeTV  = ObservableField<String>()
    var weekWorkedTimeTV       = ObservableField<String>()
    var MonthWorkedTimeTV      = ObservableField<String>()

    private var remainingTime = 7 * 60 * 60 * 1000L
    private var TotalTime     = 7 * 60 * 60 * 1000L
    private var job: Job?     = null
    private var isTimerRunning= false
    private val calendar = Calendar.getInstance()


    private var yesterdayWorkedTime = 0L
    private var weekWorkedTime      = 0L
    private var monthWorkedTime     = 0L

    lateinit var startButton: AppCompatImageButton
    lateinit var pauseButton: AppCompatImageButton

//    fun setWorkedTimes(){
//        val db = FirebaseFirestore.getInstance()
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        val userRef = db.collection(AppUser.COLLECTION_NAME).document(currentUser!!.uid)
//
//        userRef.get().addOnSuccessListener { documentSnapshot ->
//            val user = documentSnapshot.toObject(AppUser::class.java)
//
//            yesterdayWorkedTime = user!!.yesterday!!
//            weekWorkedTime = user.week!!
//            monthWorkedTime = user.month!!
//
//            updateViews(yesterdayWorkedTime, weekWorkedTime, monthWorkedTime)
//        }.addOnFailureListener { e ->
//            // handle failure
//        }
//
//
//
//    }

    fun updateViews() {

        val yesterdayWorkedTimeInMinutes = (yesterdayWorkedTime / 1000) /60
        val yesterdayHourss = yesterdayWorkedTimeInMinutes / 60
        val yesterdayMinutes = yesterdayWorkedTimeInMinutes % 60

        yesterdayWorkedTimeTV.set("${yesterdayHourss}h ${yesterdayMinutes}m")
        yesterdayWorkedTimeTV.notifyChange()

        val weekWorkedTimeInMinutes = (weekWorkedTime / 1000) /60
        val weekHourss = weekWorkedTimeInMinutes / 60
        val weekMinutes = weekWorkedTimeInMinutes % 60

        weekWorkedTimeTV.set("${weekHourss}h ${weekMinutes}m")
        weekWorkedTimeTV.notifyChange()

        val monthWorkedTimeInMinutes = (monthWorkedTime / 1000) /60
        val monthHourss = monthWorkedTimeInMinutes / 60
        val monthMinutes = monthWorkedTimeInMinutes % 60

        MonthWorkedTimeTV.set("${monthHourss}h ${monthMinutes}m")
        MonthWorkedTimeTV.notifyChange()


    }

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
            startButton.visibility = View.GONE
            pauseButton.visibility = View.VISIBLE
            isTimerRunning = true
        }
    }


    @JvmName("getTimer1")
    fun getTimer() = timer

    // Method to make the text timer -- change text to current time
    private suspend fun updateTimerText() {
        val hours   = remainingTime / (60 * 60 * 1000) % 24
        val minutes = remainingTime / (60 * 1000) % 60
        val seconds = remainingTime / 1000 % 60
        val timerText = "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
        withContext(Dispatchers.Main) {
            timer.set(timerText)
        }
    }
    // Method to stop the timer
//    fun stop() {
//        job?.cancel()
//        // save worked time to yesterday
//        yesterdayWorkedTime = remainingTime
//        saveWorkedTimetoYesterdayFieldInTheFireStore()
//        // add worked time to the week
//        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
//        if (currentDay != Calendar.FRIDAY && currentDay != Calendar.SATURDAY) {
//            weekWorkedTime += yesterdayWorkedTime
//            monthWorkedTime += yesterdayWorkedTime
//        }
//        val currentDate = Calendar.getInstance().time
//        val endOfWeek = Calendar.getInstance()
//        endOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
//        if (currentDate >= endOfWeek.time) {
//            // it's the end of the week, save the worked time to firestore
//            saveWorkedTimetoWeekFieldInTheFireStore()
//
//            // add worked time to the month
//            monthWorkedTime += weekWorkedTime
//
//            val endOfMonth = Calendar.getInstance()
//            endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH))
//            if (currentDate >= endOfMonth.time) {
//                // it's the end of the month, save the worked time to firestore
//                saveWorkedTimetoMonthFieldInTheFireStore()
//
//                // reset the month because we will start a new month
//                monthWorkedTime = 0
//            }
//
//            // reset the week because we will start a new week
//            weekWorkedTime = 0
//        }
//        updateViews(yesterdayWorkedTime, weekWorkedTime, monthWorkedTime)
//    }

    fun stop() {
        if (isTimerRunning){
            job?.cancel()
            // save worked time to yesterday
            yesterdayWorkedTime =  TotalTime - remainingTime
            saveWorkedTimetoYesterdayFieldInTheFireStore()
            // add worked time to the week
            weekWorkedTime += yesterdayWorkedTime
            monthWorkedTime += weekWorkedTime
            val currentDate = Calendar.getInstance().time
            val endOfWeek = Calendar.getInstance()
            endOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
            if (currentDate <= endOfWeek.time) {
                // it's the end of the week, save the worked time to firestore
                saveWorkedTimetoWeekFieldInTheFireStore()

                // add worked time to the month
                monthWorkedTime += weekWorkedTime

                val endOfMonth = Calendar.getInstance()
                endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH))
                if (currentDate <= endOfMonth.time) {
                    // it's the end of the month, save the worked time to firestore
                    saveWorkedTimetoMonthFieldInTheFireStore()
                } else {
                    // reset the month because we will start a new month
                    monthWorkedTime = 0
                }
            } else {
                // reset the week because we will start a new week
                weekWorkedTime = 0
            }
            updateViews()
            isTimerRunning = false
        }
    }
    private fun saveWorkedTimetoYesterdayFieldInTheFireStore() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        val appUserRef =
            FirebaseFirestore.getInstance().collection(AppUser.COLLECTION_NAME).document(userId)

        appUserRef.update("yesterday", yesterdayWorkedTime)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    private fun saveWorkedTimetoWeekFieldInTheFireStore() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val appUserRef =
            FirebaseFirestore.getInstance().collection(AppUser.COLLECTION_NAME).document(userId)

        appUserRef.update("month", FieldValue.increment(monthWorkedTime))
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }

    }
    private fun saveWorkedTimetoMonthFieldInTheFireStore() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val appUserRef =
            FirebaseFirestore.getInstance().collection(AppUser.COLLECTION_NAME).document(userId)

        appUserRef.update("week", FieldValue.increment(weekWorkedTime))
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }


    // Method to pause the timer
    // If user clicks on the button again it will continue the timer
    fun pause() {
        if (isTimerRunning) {
            job?.cancel()
            isTimerRunning = false
            startButton.visibility = View.VISIBLE
            pauseButton.visibility = View.GONE
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