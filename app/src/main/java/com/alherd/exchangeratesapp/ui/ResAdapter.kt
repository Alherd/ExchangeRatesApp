package com.alherd.exchangeratesapp.ui

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alherd.exchangeratesapp.R
import com.alherd.exchangeratesapp.model.Rate


/**
 * Created by Olgerd on 22.07.2018.
 */
class ResAdapter(c: Activity, rates: ArrayList<Rate>, variableId: Int) : RecyclerView.Adapter<ResAdapter.ResHolder>() {
    private var c: Activity = c
    private var mRates: ArrayList<Rate> = rates
    private var variableId: Int = variableId

    override fun onBindViewHolder(holder: ResHolder, position: Int) {
        val a: Rate = mRates.get(position)
        holder.binding.setVariable(variableId, a)
    }

    override fun getItemCount(): Int {
        return mRates.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResAdapter.ResHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.model, parent, false)
        return ResHolder(view)
    }

    class ResHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding: ViewDataBinding

        init {
            binding = DataBindingUtil.bind(v)!!
        }
    }
}