package com.alherd.exchangeratesapp.rss

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.util.concurrent.Callable


/**
 * Created by Olgerd on 21.07.2018.
 */
class DownLoader(c: Context, urlAddress: String) {

    private var c: Context = c
    private var urlAddress: String = urlAddress
    private lateinit var pd: ProgressDialog

    fun connectInputStream() {
        pd = ProgressDialog(c)
        pd.setTitle("Fetch Articles")
        pd.setMessage("Fetching...Please wait")
        pd.show()

        createObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Any> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(value: Any?) {
                        if (value.toString().startsWith("Error")) {
                            Toast.makeText(c, value.toString(), Toast.LENGTH_SHORT).show()
                        } else {
                            RSSParser(c, value as InputStream).execute()
                        }
                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onComplete() {
                        pd.dismiss()
                    }

                })
    }

    private fun createObservable(): Observable<Any> {
        return Observable.defer(object : Callable<ObservableSource<Any>> {
            override fun call(): ObservableSource<Any>? {
                return Observable.just(downloadData())
            }
        })
    }

    private fun downloadData(): Any {
        var connection: Any = Connector.connect(urlAddress)
        if (connection.toString().startsWith("Error")) {
            return connection.toString()
        }
        try {
            var con = connection as HttpURLConnection
            var responseCode: Int = con.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                var inputStream: InputStream = BufferedInputStream(con.inputStream)
                return inputStream
            }

            return ErrorTraker.RESPONSE_ERROR + con.responseMessage
        } catch (e: IOException) {
            e.printStackTrace()
            return ErrorTraker.IO_ERROR
        }
    }
}