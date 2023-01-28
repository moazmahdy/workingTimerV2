package com.example.workingtimerv2.ui.register

import androidx.databinding.ObservableField
import com.example.workingtimerv2.DataUtils
import com.example.workingtimerv2.base.BaseViewModel
import com.example.workingtimerv2.database.addUserToFirestore
import com.example.workingtimerv2.model.AppUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel: BaseViewModel<Navigator>() {

    // Variables to hold the input values for name, email, and password

    val name = ObservableField<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    // Variables to hold the error messages for name, email, and password
    val nameError = ObservableField<String>()
    val emailError = ObservableField<String>()
    val passwordError = ObservableField<String>()

    // Firebase authentication instance
    private val auth = Firebase.auth
    val user = auth.currentUser
    lateinit var userName: String

    // Method to create a new account
    fun createAccount() {
        // Validate the input fields
        if (validate()) {
            // Add the account to Firebase
            addAccountToFirebase()
        }
    }

    // Method to add the account to Firebase
    private fun addAccountToFirebase() {
        // Show loading spinner
        showLoading.value = true
        // Create the user with email and password
        auth.createUserWithEmailAndPassword(email.get()!!, password.get()!!)
            .addOnCompleteListener { task ->
                // Hide loading spinner
                showLoading.value = false
                if (!task.isSuccessful) {
                    // Show error message
                    messageLiveData.value = task.exception!!.localizedMessage
                }
                else{
                    //sendEmailVerification()
                    // Create the user with email and password
                    createFirestoreUser(task.result.user!!.uid)
                    navigator?.openEmployeeScreen(name.get().toString())
//                    AppName.userName = name.toString()
                }
            }
    }
    // Method to verify the email
//    private fun sendEmailVerification() {
//        val user = auth.currentUser
//        user?.sendEmailVerification()
//            ?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    messageLiveData.value = "Verification email sent to ${user.email}"
//                } else {
//                    messageLiveData.value = "Failed to send verification email"
//                }
//            }
//    }

    // Method to create the user in Firestore
    private fun createFirestoreUser(uid: String?) {
        //if (user != null && user.isEmailVerified)
            if (user != null){
            // Create the user object
            val user = AppUser(id = uid, name = name.get(), email = email.get())
            // Add the user to Firestore
            addUserToFirestore(user, {
                showLoading.value = false
                // Save the user object to a DataUtils class
                DataUtils.user = user
                // Navigate to the home screen
                navigator?.openEmployeeScreen(user.name!!)
            }, {
                // Hide loading spinner
                showLoading.value = false
                // Show error message
                messageLiveData.value = it.localizedMessage
            })
        } else {
            messageLiveData.value = "Please verify your email before proceeding"
        }
    }

    // Method to validate the input fields
    private fun validate(): Boolean {
        var valid = true
        if (name.get().isNullOrBlank()) {
            nameError.set("Enter name")
            valid = false
        } else {
            nameError.set(null)
        }
        if (email.get().isNullOrBlank()) {
            emailError.set("Enter email")
            valid = false
        } else {
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()){
            passwordError.set("Enter password")
            valid = false
        } else {
            passwordError.set(null)
        }
        return valid
    }
}