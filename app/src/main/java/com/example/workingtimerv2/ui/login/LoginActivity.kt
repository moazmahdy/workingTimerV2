package com.example.workingtimerv2.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.workingtimerv2.Navigator
import com.example.workingtimerv2.R
import com.example.workingtimerv2.base.BaseActivity
import com.example.workingtimerv2.databinding.ActivityLoginBinding
import com.example.workingtimerv2.model.AppUser
import com.example.workingtimerv2.ui.home.HomeActivity
import com.example.workingtimerv2.ui.register.RegisterActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() , Navigator{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.signUptxt.setOnClickListener {
            ClickEvent ->
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        viewDataBinding.vm = viewModel
        viewModel.navigator = this

    }

    override fun openLoginScreen(){}
    override fun openHomeScreen(userName: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("name", userName)
        startActivity(intent)
    }


    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initViewModel(): LoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
}