package com.example.workingtimerv2

import com.example.workingtimerv2.model.AppUser

interface Navigator {
    fun openLoginScreen()
    fun openHomeScreen(userName: String)
}