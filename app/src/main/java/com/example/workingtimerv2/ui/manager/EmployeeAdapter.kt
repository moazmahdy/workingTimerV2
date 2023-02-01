package com.example.workingtimerv2.ui.manager

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.workingtimerv2.Constants
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
        val item = items!![position]
        holder.bind(item)
        holder.hourTv.text = (getMonthHour(item!!))
        holder.salaryTv.text = (getMonthSalary(item!!) + "$")
    }

    override fun getItemCount(): Int {
        return items?.size?: 0
    }

    private fun getMonthHour(item: AppUser): String{
        val monthWorkedTimeInMinutes = ((item.month)!! / 1000) /60
        val monthHours = monthWorkedTimeInMinutes / 60
        return monthHours.toString()
    }

    private fun getMonthSalary(item: AppUser): String{
        val monthWorkedTimeInMinutes = ((item.month)!! / 1000) /60
        val monthHours = monthWorkedTimeInMinutes / 60
        val totalSalary = monthHours * Constants.SALARY_BER_HOUR
        return totalSalary.toString()
    }

    fun changeData(rooms: List<AppUser>) {
        items = rooms
        notifyDataSetChanged()
    }

    class ViewHolder(private val viewDataBinding: EmployeeItemBinding)
        : RecyclerView.ViewHolder(viewDataBinding.root){

        lateinit var hourTv: TextView
        lateinit var salaryTv: TextView

        fun bind(user: AppUser?){
            viewDataBinding.item = user
            viewDataBinding.invalidateAll()
            hourTv = itemView.findViewById(R.id.item_hours_tv)
            salaryTv = itemView.findViewById(R.id.item_salary)
        }
    }
}