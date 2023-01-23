package com.example.workingtimerv2.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open abstract class BaseActivity<DB: ViewDataBinding , VM: BaseViewModel<*>>: AppCompatActivity() {

    lateinit var viewModel:VM
    lateinit var viewDataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this,getLayoutId())
        viewModel = initViewModel()
        subscribeToLiveData()
    }

    fun subscribeToLiveData(){
        viewModel.messageLiveData.observe(this) {
            showDialog(it)
        }

        viewModel.showLoading.observe(this) {
            if (it) showLoading()
            else hideLoading()
        }
    }

    var alertDialog : AlertDialog?= null
    fun showDialog(message: String,
                   posActionName:String? = null, posAction: DialogInterface.OnClickListener?=null,
                   negActionName:String? = null, negAction: DialogInterface.OnClickListener?=null,
                   cancelable : Boolean = true){

        val defAction = DialogInterface.OnClickListener { dialog, which -> dialog!!.dismiss() }
        val builder = AlertDialog.Builder(this).setMessage(message)
        if (posActionName!=null)  builder.setPositiveButton(posActionName, posAction ?: defAction)
        if (negActionName!=null)  builder.setPositiveButton(negActionName, negAction ?: defAction)
        builder.setCancelable(cancelable)

        alertDialog = builder.show()
    }

    fun hideDialog(){
        alertDialog?.dismiss()
        alertDialog = null
    }


    var progressDialog: ProgressDialog? = null
    fun showLoading(){
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("Loading....")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }
    fun hideLoading(){
        progressDialog?.dismiss()
        progressDialog = null
    }

    abstract fun getLayoutId():Int
    abstract fun initViewModel():VM
}