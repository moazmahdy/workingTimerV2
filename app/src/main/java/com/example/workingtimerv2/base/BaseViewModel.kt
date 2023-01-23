package com.example.workingtimerv2.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<N>:ViewModel() {

    var navigator: N? = null
    val showLoading = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<String>()
}