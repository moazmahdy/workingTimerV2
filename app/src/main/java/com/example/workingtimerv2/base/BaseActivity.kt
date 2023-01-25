package com.example.workingtimerv2.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<DB: ViewDataBinding, VM: BaseViewModel<*>>: AppCompatActivity() {

    // Variables to hold the viewmodel and viewdatabinding instances
    lateinit var viewModel: VM
    lateinit var viewDataBinding: DB

    // Overriding the onCreate method to set the content view, initialize the viewmodel and subscribe to live data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = initViewModel()
        subscribeToLiveData()
    }

    // Method to subscribe to live data and update the UI accordingly
    fun subscribeToLiveData(){
        viewModel.messageLiveData.observe(this) {
            showDialog(it)
        }

        viewModel.showLoading.observe(this) {
            if (it) showLoading()
            else hideLoading()
        }
    }

    // Variable to hold the alert dialog
    var alertDialog : AlertDialog?= null

    // Method to show a dialog with given message, positive and negative action buttons, and cancelability
    private fun showDialog(
        message: String,
        posActionName: String? = null, posAction: DialogInterface.OnClickListener? = null,
        negActionName: String? = null, negAction: DialogInterface.OnClickListener? = null,
        cancelable: Boolean = true
    ) {

        // Default action to dismiss the dialog
        val defAction = DialogInterface.OnClickListener { dialog, which -> dialog!!.dismiss() }

        // Creating the dialog builder with the given message
        val builder = AlertDialog.Builder(this).setMessage(message)

        if (posActionName != null) builder.setPositiveButton(posActionName, posAction ?: defAction)

        if (negActionName != null) builder.setPositiveButton(negActionName, negAction ?: defAction)

        // Setting the cancellability of the dialog
        builder.setCancelable(cancelable)

        // Showing the dialog
        alertDialog = builder.show()
    }

    // Method to hide the dialog
    fun hideDialog(){
        alertDialog?.dismiss()
        alertDialog = null
    }

    // Variable to hold the progress dialog
    var progressDialog: ProgressDialog? = null

    // Method to show the loading progress dialog
    fun showLoading(){
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("Loading....")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    // Method to hide the loading progress dialog
    fun hideLoading(){
        progressDialog?.dismiss()
        progressDialog = null
    }

    abstract fun getLayoutId(): Int
    abstract fun initViewModel(): VM
}
