
package com.example.workingtimerv2.ui.employee


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModelProvider
import com.example.workingtimerv2.R
import com.example.workingtimerv2.base.BaseActivity
import com.example.workingtimerv2.databinding.ActivityEmployeeBinding
import com.example.workingtimerv2.model.AppUser
import com.example.workingtimerv2.ui.login.LoginActivity

class EmployeeActivity : BaseActivity<ActivityEmployeeBinding, EmployeeViewModel>(), Navigator {

    lateinit var user: AppUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val binding = DataBindingUtil.setContentView<ActivityEmployeeBinding>(this, R.layout.activity_employee)
        //val viewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
        //binding.vm = viewModel

        viewDataBinding.vm = viewModel
        viewModel.navigator = this
        // I call the startTimer function from viewModel to start the timer when Activity is created
        // When activity is created means the user is successfully signed in
        user = intent.getParcelableExtra("name")!!
        viewModel.setDate()
        viewModel.setUserName(user.name!!)
        viewModel.setStartTimerText()
        viewModel.startButton = viewDataBinding.startButton
        viewModel.pauseButton = viewDataBinding.pauseButton
        viewModel.updateViews(user.yesterday!!, user.week!!, user.month!!)

        val context = this
        viewModel.context = context

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("timer_notification", "Timer", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.updateViews(user.yesterday!!, user.week!!, user.month!!)
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_employee
    }

    override fun initViewModel(): EmployeeViewModel {
        return ViewModelProvider(this)[EmployeeViewModel::class.java]

    }

    override fun openLoginScreen() {
        Toast.makeText(this, "you're log out", Toast.LENGTH_LONG)
            .show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}