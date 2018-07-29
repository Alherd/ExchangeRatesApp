package com.alherd.exchangeratesapp.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.alherd.exchangeratesapp.model.Rate

/**
 * Created by Olgerd on 22.07.2018.
 */
class RateViewModel(val a: Rate) : BaseObservable() {
    private var rate: Rate = a

    @Bindable
    public fun getName(): String {
        return rate.name
    }
}