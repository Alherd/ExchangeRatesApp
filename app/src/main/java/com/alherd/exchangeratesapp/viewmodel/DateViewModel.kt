package com.alherd.exchangeratesapp.viewmodel

import java.util.*

class DateViewModel(date1: String) {
    private val date: String = date1
    fun getDate(): String {
        return date
    }
}