package com.alherd.exchangeratesapp

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.alherd.exchangeratesapp.databinding.ContentMainBinding
import com.alherd.exchangeratesapp.rss.DownLoader

class MainActivity : AppCompatActivity() {
    companion object {
        val URL_ADDRESS: String = "http://www.nbrb.by/Services/XmlExRates.aspx"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)
        val linearLayoutManager = LinearLayoutManager(this)
        val contentMainBinding: ContentMainBinding = DataBindingUtil.setContentView(this, R.layout.content_main)
        contentMainBinding.recycler.layoutManager = linearLayoutManager
        DownLoader(this, URL_ADDRESS, contentMainBinding).execute()

    }

}
