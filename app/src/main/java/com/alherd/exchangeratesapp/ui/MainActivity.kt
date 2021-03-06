package com.alherd.exchangeratesapp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alherd.exchangeratesapp.R
import com.alherd.exchangeratesapp.rss.DownLoader

class MainActivity : AppCompatActivity() {
    companion object {
        const val URL_ADDRESS: String = "http://www.nbrb.by/Services/XmlExRates.aspx"
        const val TAG: String = "MainActivityTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        DownLoader(this, URL_ADDRESS).downLoadInputStream()

    }

}
