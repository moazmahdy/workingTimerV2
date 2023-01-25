package com.example.workingtimerv2.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.workingtimerv2.Navigator
import com.example.workingtimerv2.R
import com.example.workingtimerv2.base.BaseActivity
import com.example.workingtimerv2.databinding.ActivityRegisterBinding
import com.example.workingtimerv2.ui.home.HomeActivity
import com.example.workingtimerv2.ui.login.LoginActivity


class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(), Navigator{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Navigate to LoginScreen
        viewDataBinding.SignInTxt.setOnClickListener{
            clickEvent ->
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        viewDataBinding.vm = viewModel
        viewModel.navigator = this
    }

    override fun getLayoutId(): Int = R.layout.activity_register

    override fun initViewModel(): RegisterViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

    override fun openLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun openHomeScreen() {
        TODO("Not yet implemented")
    }

}