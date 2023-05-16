package com.developerscracks.searchviewasistida.data

object ExpenseRepository {

    private val expenses: MutableList<Expense>

    init {
        expenses = mutableListOf(
            Expense(1, "Salida a cine", "09/06/2021", 15.0),
            Expense(2, "Compra de viveres", "08/06/2021", 100.0),
            Expense(3, "Pago de arriendo", "07/06/2021", 150.0),
            Expense(4, "Pago de servicios públicos", "06/06/2021", 55.0),
            Expense(5, "Un mecatico", "06/06/2021", 2.0),
            Expense(6, "Netflix Junio", "06/06/2021", 10.0),
            Expense(7, "Compra de steam", "01/06/2021", 15.0),
            Expense(8, "Compra de viveres en la 14", "01/06/2021", 40.0),
            Expense(9, "Pago mensual de correo", "25/05/2021", 5.0),
            Expense(10, "Salida con amigos", "20/05/2021", 70.0)
        )
    }

    fun getAll(): List<Expense> = expenses

    fun search(query: String): List<Expense> {
        if (query.isBlank())
            return emptyList()

        return expenses.filter { expense ->
            val regex = query.toRegex(RegexOption.IGNORE_CASE)
            regex.containsMatchIn(expense.description)
        }
    }
}