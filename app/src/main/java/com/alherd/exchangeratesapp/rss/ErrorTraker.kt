package com.alherd.exchangeratesapp.rss

/**
 * Created by Olgerd on 21.07.2018.
 */
class ErrorTraker {
    companion object {
        val WRONG_URL_FORMAT: String = "Error : URL Format is not valid"
        val CONNECTION_ERROR: String = "Error : CONNECTION_ERROR"
        val IO_ERROR: String = "Error : IO_ERROR"
        val RESPONSE_ERROR: String = "Error : RESPONSE_ERROR"
    }
}