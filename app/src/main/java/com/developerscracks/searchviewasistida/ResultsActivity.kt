package com.developerscracks.searchviewasistida

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.developerscracks.searchviewasistida.adapter.ExpenseAdapter
import com.developerscracks.searchviewasistida.data.ExpenseRepository

class ResultsActivity : AppCompatActivity() {
    private lateinit var expenseList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpList()

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                // Buscar datos
                searchExpenses(query)
            }
        }
    }

    private fun searchExpenses(query: String) {
        val searchResults = ExpenseRepository.search(query)
        (expenseList.adapter as ExpenseAdapter).submitList(searchResults)
    }

    private fun setUpList() {
        expenseList = findViewById(R.id.list)

        val adapter = ExpenseAdapter()
        expenseList.adapter = adapter
    }
}