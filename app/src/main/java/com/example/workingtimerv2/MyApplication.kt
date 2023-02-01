package com.example.workingtimerv2

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
    }
}