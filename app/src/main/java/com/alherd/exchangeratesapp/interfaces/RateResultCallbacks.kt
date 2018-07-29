package com.alherd.exchangeratesapp.interfaces

interface RateResultCallbacks {
    fun onSuccess(message: String)
    fun onError(message: String)
}