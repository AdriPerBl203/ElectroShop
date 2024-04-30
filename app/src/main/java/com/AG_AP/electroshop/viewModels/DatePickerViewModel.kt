package com.AG_AP.electroshop.viewModels

interface DatePickerViewModel {
    val selectedDate: String
    fun onDateSelected(date: String)
}