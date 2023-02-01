package com.example.workingtimerv2.ui.register

import com.example.workingtimerv2.model.AppUser

interface Navigator {

    fun openLoginScreen()

    fun openEmployeeScreen(user: AppUser)
}