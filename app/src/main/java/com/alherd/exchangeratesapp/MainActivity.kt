package com.alherd.exchangeratesapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import butterknife.BindView
import butterknife.ButterKnife
import com.alherd.exchangeratesapp.rss.DownLoader

class MainActivity : AppCompatActivity() {
    companion object {
        val URL_ADDRESS: String = "http://www.nbrb.by/Services/XmlExRates.aspx"
    }

 //   @BindView(R.id.lv)
    lateinit var lv: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        lv = findViewById(R.id.lv)
        DownLoader(this, URL_ADDRESS, lv).execute()
    }
}
