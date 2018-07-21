package com.alherd.exchangeratesapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.alherd.exchangeratesapp.R
import com.alherd.exchangeratesapp.data_objects.Article

/**
 * Created by Olgerd on 21.07.2018.
 */
class CustomAdapter(c: Context, articles: ArrayList<Article>) : BaseAdapter() {

    private var c: Context = c
    private var articles: ArrayList<Article> = articles

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var p3 = p1
        if (p3 == null) {
            p3 = LayoutInflater.from(c).inflate(R.layout.model, p2, false)
        }
        var nameText: TextView = p3!!.findViewById(R.id.name_rate)

        var article: Article = this.getItem(p0) as Article

        val name: String = article.name
        nameText.setText(name)
        return p3!!
    }

    override fun getItem(p0: Int): Any {
        return articles.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return articles.size
    }
}