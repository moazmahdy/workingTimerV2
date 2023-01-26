package com.example.workingtimerv2.ui.home


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.workingtimerv2.R
import com.example.workingtimerv2.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        val viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        binding.vm = viewModel
        // I call the startTimer function from viewModel to start the timer when Activity is created
        // When activity is created means the user is successfully signed in
        viewModel.startTimer()

    }
}