package com.example.workingtimerv2.ui.manager

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.workingtimerv2.R
import com.example.workingtimerv2.base.BaseActivity
import com.example.workingtimerv2.database.getUsers
import com.example.workingtimerv2.databinding.ActivityManagerBinding
import com.example.workingtimerv2.model.AppUser

class ManagerActivity: BaseActivity<ActivityManagerBinding, ManagerViewModel>(), Navigator {

    private val adapter = EmployeeAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel
        viewModel.navigator = this
        viewDataBinding.recyclerview.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        getUsers({
            val users = it.toObjects(AppUser::class.java)
            adapter.changeData(users)
        }, {
            Toast.makeText(this , "can't fetch users", Toast.LENGTH_LONG)
                .show()
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_manager
    }

    override fun initViewModel(): ManagerViewModel {
        return ViewModelProvider(this)[ManagerViewModel::class.java]
    }
}