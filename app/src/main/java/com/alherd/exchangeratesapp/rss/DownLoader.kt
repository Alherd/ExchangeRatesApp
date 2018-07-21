package com.alherd.exchangeratesapp.rss

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.ListView
import android.widget.Toast
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection

/**
 * Created by Olgerd on 21.07.2018.
 */
class DownLoader(c: Context, urlAddress: String, lv: ListView) : AsyncTask<Void, Void, Any>() {

    private var c: Context = c
    private var urlAddress: String = urlAddress
    private var lv: ListView = lv
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
        if(result.toString().startsWith("Error")){
            Toast.makeText(c, result.toString(), Toast.LENGTH_SHORT).show()
        } else{
            RSSParser(c,result as InputStream, lv).execute()
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