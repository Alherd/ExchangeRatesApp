package com.alherd.exchangeratesapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.alherd.exchangeratesapp.rss.DownLoader

class MainActivity : AppCompatActivity() {
    companion object {
        val URL_ADDRESS: String = "http://www.nbrb.by/Services/XmlExRates.aspx"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)
        val recyclerView: RecyclerView = findViewById(R.id.recycler)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        DownLoader(this, URL_ADDRESS, recyclerView).execute()


    }

}
