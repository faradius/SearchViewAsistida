package com.developerscracks.searchviewasistida.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.developerscracks.searchviewasistida.R
import com.developerscracks.searchviewasistida.data.Expense
import java.text.NumberFormat

class ExpenseAdapter: ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.expense_item, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = getItem(position)
        holder.bind(expense)
    }

    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val description: TextView = view.findViewById(R.id.description)
        private val date: TextView = view.findViewById(R.id.date)
        private val total: TextView = view.findViewById(R.id.total)
        fun bind(expense: Expense) {
            description.text = expense.description
            date.text = expense.date
            total.text = NumberFormat.getCurrencyInstance().format(expense.total)
        }
    }
}

class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
    override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
        return oldItem == newItem
    }
}