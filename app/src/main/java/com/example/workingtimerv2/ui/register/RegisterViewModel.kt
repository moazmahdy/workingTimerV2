package com.example.workingtimerv2.ui.register

import android.util.Log
import androidx.databinding.ObservableField
import com.example.workingtimerv2.DataUtils
import com.example.workingtimerv2.base.BaseViewModel
import com.example.workingtimerv2.database.addUserToFirestore
import com.example.workingtimerv2.model.AppUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel: BaseViewModel<Navigator>() {

    val name = ObservableField<String>()
    val nameError = ObservableField<String>()
    val email = ObservableField<String>()
    val emailError = ObservableField<String>()
    val password = ObservableField<String>()
    val passwordError = ObservableField<String>()
    private val auth = Firebase.auth

    fun createAccount(){
        Log.e("click" , "createAccount")
        if (validate()){
            addAccountToFirebase()
        }
    }

    private fun addAccountToFirebase() {
        showLoading.value = true
        auth.createUserWithEmailAndPassword(email.get()!!, password.get()!!)
            .addOnCompleteListener { task->
                showLoading.value = false
                if (!task.isSuccessful) {
                    //show error message
                    messageLiveData.value = task.exception!!.localizedMessage
                } else {
                    //show success message
                    messageLiveData.value = "success registration"
                    //navigator?.openHomeScreen()
                    createFirestoreUser(task.result.user?.uid)
                }
            }
    }

    private fun createFirestoreUser(uid: String?) {
        showLoading.value = false
        val user = AppUser(name = name.get(),
            email = email.get())
        addUserToFirestore(user, {
            showLoading.value = false
            DataUtils.user = user
            navigator?.openHomeScreen()
        }, {
            showLoading.value = false
            messageLiveData.value = it.localizedMessage
        })
    }

    private fun validate(): Boolean {
        var valid = true
        if (name.get().isNullOrBlank()){
            nameError.set("Enter name")
            valid = false
        } else {
            nameError.set(null)
        }
        if (email.get().isNullOrBlank()){
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