package com.alherd.exchangeratesapp.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alherd.exchangeratesapp.R
import com.alherd.exchangeratesapp.model.Currency
import com.alherd.exchangeratesapp.viewmodel.CurrencyViewModel

/**
 * Created by Olgerd on 22.07.2018.
 */
class ResAdapter(c: Context, currencies: ArrayList<Currency>, variableId: Int) : RecyclerView.Adapter<ResAdapter.ResHolder>() {
    private var c: Context = c
    private var mCurrencies: ArrayList<Currency> = currencies
    private var variableId: Int = variableId

    override fun onBindViewHolder(holder: ResHolder, position: Int) {
        val currency: Currency = mCurrencies.get(position)
        val rateViewModel = CurrencyViewModel(currency)
        holder.binding.setVariable(variableId, rateViewModel)
    }

    override fun getItemCount(): Int {
        return mCurrencies.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.model, parent, false)
        return ResHolder(view)
    }

    class ResHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding: ViewDataBinding = DataBindingUtil.bind(v)!!

    }
}