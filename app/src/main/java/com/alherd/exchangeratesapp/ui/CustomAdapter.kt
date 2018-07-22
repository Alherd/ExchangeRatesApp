package com.alherd.exchangeratesapp.ui

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.alherd.exchangeratesapp.R
import com.alherd.exchangeratesapp.databinding.ModelBinding
import com.alherd.exchangeratesapp.model.Rate

/**
 * Created by Olgerd on 21.07.2018.
 */
class CustomAdapter(c: Activity, rates: ArrayList<Rate>) : BaseAdapter() {

    private var c: Activity = c
    private var mRates: ArrayList<Rate> = rates

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var p3 = p1
        var l: Int = R.layout.model
        val activityMainBinding: ModelBinding = DataBindingUtil.setContentView(c, l)

        //      var nameText: TextView = p3!!.findViewById(R.id.name_rate)

        var rate: Rate = this.getItem(p0) as Rate

        //     val name: String = rate.name
        // activityMainBinding.viewModel = rate
        activityMainBinding.rate = rate

        //             nameText.setText(name)
        if (p3 == null) {
            p3 = LayoutInflater.from(c).inflate(l, p2, false)
        }
        return p3!!
    }

    override fun getItem(p0: Int): Any {
        return mRates.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        Log.d("qqqqqqqqqq", mRates.size.toString())
        return mRates.size
    }
}