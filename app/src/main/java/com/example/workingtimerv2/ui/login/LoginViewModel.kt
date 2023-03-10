package com.example.workingtimerv2.ui.login

import androidx.databinding.ObservableField
import com.example.workingtimerv2.Constants
import com.example.workingtimerv2.DataUtils
import com.example.workingtimerv2.base.BaseViewModel
import com.example.workingtimerv2.database.signIn
import com.example.workingtimerv2.model.AppUser
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : BaseViewModel<Navigator>() {

    // Variables to hold the input values for name, email, and password
    val email = object: ObservableField<String>() {
        override fun set(value: String?) {
            super.set(value?.trim())
        }
    }
    val password = ObservableField<String>()

    // Variables to hold the error messages for name, email, and password
    val emailError = ObservableField<String>()
    val passwordError = ObservableField<String>()

    private val firebaseAuth = Firebase.auth
    fun login() {
        if (validate()) {
            authWithFirebaseAuth()
        }
    }

    private fun authWithFirebaseAuth() {
        showLoading.value = true
        firebaseAuth.signInWithEmailAndPassword(email.get()!!, password.get()!!)
            .addOnCompleteListener { task ->
                showLoading.value = false
                if (!task.isSuccessful) {
                    messageLiveData.value = task.exception?.localizedMessage
                } else {
                    val user = task.result.user
                    //if(user != null && user.isEmailVerified)
                    if (user != null) {
                        //    checkUserFromFireStore(task.result.user?.uid)
                        checkUserInFireStore(user.uid)
                        //navigator?.openHomeScreen(user.displayName.toString())
                        //navigator?.openHomeScreen(RegisterViewModel.name.get().toString())
                    } else {
                        messageLiveData.value =
                            "Email is not verified, please verify your email before logging in."
                    }
                }
            }
        }


    // Method to check if the user exists
    private fun checkUserInFireStore(uid: String?) {
        showLoading.value = true
        signIn(uid!!,
            OnSuccessListener { docSnapshot ->
                showLoading.value = false
                val user = docSnapshot.toObject(AppUser::class.java)
                // if user is not in the FireStore we show an error message
                if (user == null) {
                    messageLiveData.value = "Invalid Email or Password"
                    return@OnSuccessListener
                } else {
                    DataUtils.user = user
                    if (user.id == Constants.MANAGER_ID){
                        navigator?.openMangerScreen()
                    } else {
                        navigator?.openEmployeeScreen(user)
                    }
                }
            }, onFailureListener = {
                showLoading.value = false
                messageLiveData.value = it.localizedMessage
            })
        }

    private fun validate(): Boolean {
        var isValid = true
        if (email.get().isNullOrBlank()) {
            emailError.set("Please enter email")
            isValid = false
        } else {
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()) {
            passwordError.set("Please enter password")
            isValid = false
        } else {
            passwordError.set(null)
        }
        return isValid
    }

    fun openRegister(){
        navigator?.openRegister()
    }
}