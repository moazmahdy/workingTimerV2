package com.example.workingtimerv2.ui.manager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.workingtimerv2.R
import com.example.workingtimerv2.databinding.EmployeeItemBinding
import com.example.workingtimerv2.model.AppUser

class EmployeeAdapter(var items:List<AppUser?>?): Adapter<EmployeeAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: EmployeeItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.employee_item, parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items!![position])
    }

    override fun getItemCount(): Int {
        return items?.size?: 0
    }

    fun changeData(rooms: List<AppUser>) {
        items = rooms
        notifyDataSetChanged()
    }

    class ViewHolder(private val viewDataBinding: EmployeeItemBinding)
        : RecyclerView.ViewHolder(viewDataBinding.root){
        fun bind(user: AppUser?){
            viewDataBinding.item = user
            viewDataBinding.invalidateAll()
        }
    }
}