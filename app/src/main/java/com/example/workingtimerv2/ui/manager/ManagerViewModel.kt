package com.example.workingtimerv2.ui.manager

import android.widget.Toast
import androidx.databinding.ObservableField
import com.example.workingtimerv2.base.BaseViewModel
import com.example.workingtimerv2.database.getUsers
import com.example.workingtimerv2.model.AppUser
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class ManagerViewModel: BaseViewModel<Navigator>(){

    val thisMonth = ObservableField<String>()
    private val calendar = Calendar.getInstance()


    fun setMonth(){
        val monthName = getMonthByNumber(calendar.get(Calendar.MONTH) + 1)
        thisMonth.set("" + monthName + " - " +
                calendar.get(Calendar.YEAR))
    }

    fun getMonthByNumber(monthnum:Int):String {
        val c = Calendar.getInstance()
        val month_date = SimpleDateFormat("MMMM")
        c[Calendar.MONTH] = monthnum-1
        return month_date.format(c.time)
    }

    fun setTotalSalary(): Long{
        var totalSalary = 0L
        getUsers({
            val users = it.toObjects(AppUser::class.java)
            for (i in 0 until users.size){
                var user = users[i]
                totalSalary+= user.month!!
            }
        },{

        })
        return totalSalary
    }

    fun setTotalHours(): Long{
        var totalHours = 0L
        getUsers({
            val users = it.toObjects(AppUser::class.java)
            for (i in 0 until users.size){
                var user = users[i]
                totalHours+= user.month!!
            }
        },{

        })
        return totalHours
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut();
        navigator?.openLoginScreen()
    }
}