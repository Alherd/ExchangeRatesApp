package com.alherd.exchangeratesapp.rss

import android.app.Activity
import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection

/**
 * Created by Olgerd on 21.07.2018.
 */
class DownLoader(c: Activity, urlAddress: String, lv: RecyclerView) : AsyncTask<Void, Void, Any>() {

    private var c: Activity = c
    private var urlAddress: String = urlAddress
    private var lv: RecyclerView = lv
    private lateinit var pd: ProgressDialog

    override fun onPreExecute() {
        super.onPreExecute()
        pd = ProgressDialog(c)
        pd.setTitle("Fetch Articles")
        pd.setMessage("Fetching...Please wait")
        pd.show()
    }

    override fun doInBackground(vararg p0: Void?): Any? {
        return this.downloadData()
    }

    override fun onPostExecute(result: Any?) {
        super.onPostExecute(result)
        pd.dismiss()
        if (result.toString().startsWith("Error")) {
            Toast.makeText(c, result.toString(), Toast.LENGTH_SHORT).show()
        } else {
            RSSParser(c, result as InputStream, lv).execute()
        }
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