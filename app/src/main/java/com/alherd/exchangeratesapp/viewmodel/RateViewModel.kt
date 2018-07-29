package com.alherd.exchangeratesapp.viewmodel

import android.databinding.BaseObservable
import com.alherd.exchangeratesapp.model.Rate

/**
 * Created by Olgerd on 22.07.2018.
 */
class RateViewModel(rate: Rate) : BaseObservable() {
    private var rateModel: Rate = rate

    fun getName(): String {
        return rateModel.name
    }
}