package com.alherd.exchangeratesapp.rss

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.alherd.exchangeratesapp.BR
import com.alherd.exchangeratesapp.R
import com.alherd.exchangeratesapp.adapter.ResAdapter
import com.alherd.exchangeratesapp.databinding.ContentMainBinding
import com.alherd.exchangeratesapp.model.Rate
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

/**
 * Created by Olgerd on 21.07.2018.
 */
class RSSParser(c: Context, inputStream: InputStream) : AsyncTask<Void, Void, Boolean>() {

    private var c: Context = c
    private var inputStream: InputStream = inputStream

    private lateinit var pd: ProgressDialog
    private var mRates: ArrayList<Rate> = ArrayList<Rate>()

    override fun onPreExecute() {
        super.onPreExecute()
        pd = ProgressDialog(c)
        pd.setTitle("Parse RSS")
        pd.setMessage("Parsing...Please wait")
        pd.show()
    }

    override fun doInBackground(vararg p0: Void?): Boolean {
        return this.parseRSS()
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        pd.dismiss()
        if (result) {
            val resAdapter = ResAdapter(c, mRates, BR.rateviewmodel)
            val linearLayoutManager = LinearLayoutManager(c)
            val contentMainBinding: ContentMainBinding = DataBindingUtil.setContentView(c as Activity?, R.layout.content_main)
            contentMainBinding.recycler.layoutManager = linearLayoutManager
            contentMainBinding.recycler.adapter = resAdapter
            resAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(c, "Unable to parse", Toast.LENGTH_SHORT).show()
        }
    }

    private fun parseRSS(): Boolean {
        try {
            var factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            factory.setNamespaceAware(true)
            var parser: XmlPullParser = factory.newPullParser()
            parser.setInput(inputStream, null)

            var event: Int = parser.eventType

            var tagValue: String? = null
            var isSiteMeta: Boolean = true

            mRates.clear()
            var rate: Rate? = Rate()

            do {
                var tagname: String? = parser.name

                when (event) {
                    XmlPullParser.START_TAG ->
                        if (tagname.equals("Currency")) {
                            rate = Rate()
                            isSiteMeta = false
                        }
                    XmlPullParser.TEXT ->
                        tagValue = parser.text
                    XmlPullParser.END_TAG -> {
                        if (!isSiteMeta) {
                            if (tagname.equals("Name")) {
                                rate?.name = tagValue!!
                                //    mRates.add(rate!!);
                            }
                        }
                        if (tagname.equals("Currency")) {
                            mRates.add(rate!!);
                            isSiteMeta = true;
                        }
                    }


                }

                event = parser.next()
            } while (event != XmlPullParser.END_DOCUMENT)
            return true
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        }
        return false
    }
}