package com.alherd.exchangeratesapp.rss

import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by Olgerd on 21.07.2018.
 */
class Connector {
    companion object {
        fun connect(urlAdress: String): Any {
            try {
                var url: URL = URL(urlAdress)
                var con: HttpURLConnection = url.openConnection() as HttpURLConnection
                con.requestMethod = "GET"
                con.connectTimeout = 15000
                con.readTimeout = 15000
                con.doInput = true

                return con
            } catch (e: MalformedURLException) {
                e.printStackTrace()
                return ErrorTraker.WRONG_URL_FORMAT
            } catch (e: IOException) {
                e.printStackTrace()
                return ErrorTraker.IO_ERROR
            }
        }
    }
}