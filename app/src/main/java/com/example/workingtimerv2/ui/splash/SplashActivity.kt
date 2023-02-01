package com.example.workingtimerv2.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.workingtimerv2.Constants
import com.example.workingtimerv2.DataUtils
import com.example.workingtimerv2.R
import com.example.workingtimerv2.database.signIn
import com.example.workingtimerv2.model.AppUser
import com.example.workingtimerv2.ui.employee.EmployeeActivity
import com.example.workingtimerv2.ui.login.LoginActivity
import com.example.workingtimerv2.ui.manager.ManagerActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed( {
            checkLoggedInUser()
        },2000)
    }

    private fun checkLoggedInUser() {
        val firebaseUser = Firebase.auth.currentUser
        if (firebaseUser == null){
            startLoginActivity()
        } else {
            signIn(firebaseUser.uid , {
                val user = it.toObject(AppUser::class.java)
                DataUtils.user = user
                if (user?.id == Constants.MANAGER_ID){
                    startManagerActivity()
                } else {
                    startEmployeeActivity(user!!)
                }
            } , {
                Toast.makeText(this , "can't login" , Toast.LENGTH_LONG)
                    .show()
                startLoginActivity()
            })
        }
    }

    private fun startManagerActivity() {
        val intent = Intent(this , ManagerActivity::class.java)
        startActivity(intent)
    }

    private fun startLoginActivity() {
        val intent = Intent(this , LoginActivity::class.java)
        startActivity(intent)
    }

    private fun startEmployeeActivity(user: AppUser) {
        val intent = Intent(this , EmployeeActivity::class.java)
        intent.putExtra("name", user)
        startActivity(intent)
    }
}