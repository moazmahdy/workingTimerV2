package com.example.workingtimerv2.ui.login

import androidx.databinding.ObservableField
import com.example.workingtimerv2.base.BaseViewModel
import com.example.workingtimerv2.Navigator
import com.example.workingtimerv2.database.signIn
import com.example.workingtimerv2.model.AppUser
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : BaseViewModel<Navigator>() {

    // Variables to hold the input values for name, email, and password
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    // Variables to hold the error messages for name, email, and password
    val emailError = ObservableField<String>()
    val passwordError = ObservableField<String>()


    private val firebaseAuth = Firebase.auth

    fun login(){
        if(validate()){
            authWithFirebaseAuth()
        }
    }

    fun authWithFirebaseAuth(){
        showLoading.value = true
        firebaseAuth.signInWithEmailAndPassword(email.get()!!, password.get()!!)
            .addOnCompleteListener{ task ->
                showLoading.value = false
                if(!task.isSuccessful){
                    messageLiveData.value = task.exception?.localizedMessage
                }
                else {
                    val user = task.result.user
                    if(user != null && user.isEmailVerified){
                        checkUserInFireStore(user.uid)
                        navigator?.openHomeScreen()
                    } else {
                        messageLiveData.value = "Email is not verified, please verify your email before logging in."
                    }
                }
            }
    }


    // Method to check if the user exists
    fun checkUserInFireStore(uid: String?) {
        showLoading.value = true
        signIn(uid!!,
            OnSuccessListener{   docSnapshot ->
                showLoading.value = false
                val user = docSnapshot.toObject(AppUser::class.java)
                // if user is not in the FireStore we show an error message
                if(user == null){
                    messageLiveData.value = "Invalid Email or Password"
                    return@OnSuccessListener
                }
                navigator?.openHomeScreen()
            }
        ) {
            showLoading.value = false
            messageLiveData.value = it.localizedMessage
        }
    }
    private fun validate(): Boolean{
        var isValid = true

        if(email.get().isNullOrBlank()){
            emailError.set("Please enter email")
            isValid = false
        }
        else{
            emailError.set(null)
        }
        if(password.get().isNullOrBlank()){
            passwordError.set("Please enter password")
            isValid = false
        }
        else{
            passwordError.set(null)
        }

        return isValid
    }

}