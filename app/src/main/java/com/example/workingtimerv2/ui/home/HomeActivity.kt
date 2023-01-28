package com.example.workingtimerv2.ui.home


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.workingtimerv2.R
import com.example.workingtimerv2.base.BaseActivity
import com.example.workingtimerv2.databinding.ActivityHomeBinding
import com.example.workingtimerv2.model.AppUser
import com.example.workingtimerv2.ui.register.RegisterViewModel
import com.google.firebase.firestore.auth.User

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        val viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        binding.vm = viewModel
        // I call the startTimer function from viewModel to start the timer when Activity is created
        // When activity is created means the user is successfully signed in
        viewModel.startTimer()
        var userName: String = intent.getStringExtra("name")!!
        viewModel.setDate()
        viewModel.setUserName(userName)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(this)[HomeViewModel::class.java]
    }
}