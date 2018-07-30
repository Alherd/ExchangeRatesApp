package com.alherd.exchangeratesapp.viewmodel

import android.databinding.BaseObservable
import com.alherd.exchangeratesapp.model.Currency

/**
 * Created by Olgerd on 22.07.2018.
 */
class CurrencyViewModel(currency: Currency) : BaseObservable() {
    private var currencyModel: Currency = currency

    fun getName(): String {
        if (currencyModel.name.length > 20) run {
            val charNameArray: CharArray = currencyModel.name.toCharArray()
            val stringBuilder: StringBuilder = StringBuilder()
            for (i in charNameArray.indices) {
                stringBuilder.append(charNameArray[i])
                if (stringBuilder.length > 20) {
                    break
                }
            }
            stringBuilder.append("...")
            return stringBuilder.toString()
        }
        return currencyModel.name
    }

    fun getScale(): String {
        return currencyModel.scale
    }

    fun getRate(): String {
        return currencyModel.rate
    }

    fun getCharCode(): String {
        return currencyModel.charCode
    }
}