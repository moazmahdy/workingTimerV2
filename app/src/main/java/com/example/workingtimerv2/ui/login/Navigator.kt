package com.example.workingtimerv2.ui.login

import com.example.workingtimerv2.model.AppUser

interface Navigator {
    fun openRegister()
    fun openEmployeeScreen(user: AppUser)
}