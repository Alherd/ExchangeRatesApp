package com.alherd.exchangeratesapp.rss

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.alherd.exchangeratesapp.BR
import com.alherd.exchangeratesapp.R
import com.alherd.exchangeratesapp.adapter.ResAdapter
import com.alherd.exchangeratesapp.databinding.ContentMainBinding
import com.alherd.exchangeratesapp.model.Currency
import com.alherd.exchangeratesapp.ui.MainActivity
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.util.concurrent.Callable

/**
 * Created by Olgerd on 21.07.2018.
 */
class RSSParser(c: Context, inputStream: InputStream) {

    private var c: Context = c
    private var inputStream: InputStream = inputStream

    private lateinit var pd: ProgressDialog
    private var mCurrencies: ArrayList<Currency> = ArrayList<Currency>()

    fun rssParse() {
        changeProgressDialog()

        createObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Boolean> {

                    override fun onSubscribe(d: Disposable?) {
                        Log.d(MainActivity.TAG, "onSubscribe: $d")
                    }

                    override fun onNext(value: Boolean) {
                        getAdapter(value)
                    }

                    override fun onError(e: Throwable?) {
                        Log.e(MainActivity.TAG, "onError: ", e)
                    }

                    override fun onComplete() {
                        pd.dismiss()
                        Log.d(MainActivity.TAG, "onComplete: ")
                    }
                })
    }

    private fun changeProgressDialog() {
        pd = ProgressDialog(c)
        pd.setTitle("Parse RSS")
        pd.setMessage("Parsing...Please wait")
        pd.show()
    }

    private fun createObservable(): Observable<Boolean> {
        return Observable.defer(object : Callable<ObservableSource<Boolean>> {
            override fun call(): ObservableSource<Boolean>? {
                return Observable.just(parseRSS())
            }
        })
    }

    private fun getAdapter(result: Boolean) {
        if (result) {
            val resAdapter = ResAdapter(c, mCurrencies, BR.currencyviewmodel)
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

            mCurrencies.clear()
            var currency: Currency? = Currency()

            do {
                var tagname: String? = parser.name

                when (event) {
                    XmlPullParser.START_TAG ->
                        if (tagname.equals("Currency")) {
                            currency = Currency()
                            isSiteMeta = false
                        }
                    XmlPullParser.TEXT ->
                        tagValue = parser.text
                    XmlPullParser.END_TAG -> {
                        if (!isSiteMeta) {
                            if (tagname.equals("Name")) {
                                currency?.name = tagValue!!
                            }
                            if (tagname.equals("Scale")) {
                                currency?.scale = tagValue!!
                            }
                            if (tagname.equals("Rate")) {
                                currency?.rate = tagValue!!
                            }
                            if (tagname.equals("CharCode")) {
                                currency?.charCode = tagValue!!
                            }
                        }
                        if (tagname.equals("Currency")) {
                            mCurrencies.add(currency!!)
                            isSiteMeta = true
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