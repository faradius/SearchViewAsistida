package com.developerscracks.searchviewasistida

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.developerscracks.searchviewasistida.adapter.ExpenseAdapter
import com.developerscracks.searchviewasistida.data.ExpenseRepository

class MainActivity : AppCompatActivity() {

    private lateinit var expenseList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpList()

        handleSearchIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSearchIntent(intent)
    }

    private fun handleSearchIntent(intent: Intent) {
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

        setExpensesInitialState()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.expenses_menu, menu)

        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val menuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView

        searchView.setSearchableInfo(
            sm.getSearchableInfo(componentName)
        )
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if(!newText.isNullOrBlank())
                    searchExpenses(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

        menuItem.setOnActionExpandListener(object :
            MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                searchExpenses("")
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                setExpensesInitialState()
                return true
            }
        })

        return true
    }

    private fun setExpensesInitialState() {
        val all = ExpenseRepository.getAll()
        (expenseList.adapter as ExpenseAdapter).submitList(all)
    }
}