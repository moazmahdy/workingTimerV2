package com.example.workingtimerv2.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.workingtimerv2.R
import com.example.workingtimerv2.base.BaseActivity
import com.example.workingtimerv2.databinding.ActivityLoginBinding
import com.example.workingtimerv2.model.AppUser
import com.example.workingtimerv2.ui.employee.EmployeeActivity
import com.example.workingtimerv2.ui.manager.ManagerActivity
import com.example.workingtimerv2.ui.register.RegisterActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() , Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.signUptxt.setOnClickListener {
            ClickEvent ->
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        viewDataBinding.vm = viewModel
        viewModel.navigator = this

    }

    override fun openRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun openEmployeeScreen(user: AppUser) {
        val intent = Intent(this, EmployeeActivity::class.java)
        intent.putExtra("name", user)
        startActivity(intent)
    }

    override fun openMangerScreen() {
        val intent = Intent(this, ManagerActivity::class.java)
        startActivity(intent)
    }


    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initViewModel(): LoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
}