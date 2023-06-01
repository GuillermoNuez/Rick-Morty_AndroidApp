package com.everis.rickmorty.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everis.rickmorty.ui.main.ModelResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainViewModel : ViewModel() {
    private val _userProfileResponse = MutableLiveData<ModelResponse>()
    val userProfileResponse: LiveData<ModelResponse> = _userProfileResponse

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun getData() {
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                var httpURLConnection: HttpURLConnection? = null
                try {
                    val url = URL("https://rickandmortyapi.com/api/character")
                    httpURLConnection = url.openConnection() as HttpURLConnection
                    val code = httpURLConnection.responseCode
                    val message = httpURLConnection.responseMessage

                    println("Response Code: $code")
                    println("Response Message: $message")

                    if (code != 200) {
                        println("ERROR Code :: $code")
                    }

                    val jsonStringHolder = StringBuilder()

                    BufferedReader(InputStreamReader(httpURLConnection.inputStream)).use { reader ->
                        jsonStringHolder.append(reader.readText())
                    }

                    jsonStringHolder.toString()
                } catch (ioexception: IOException) {
                    "ERROR: Something went wrong"
                } finally {
                    httpURLConnection?.disconnect()
                }
            }

            if (result.isNotEmpty()) {
                val userProfileResponse = Gson().fromJson(result, ModelResponse::class.java)
                _userProfileResponse.value = userProfileResponse
            } else {
                _error.value = true
            }
        }
    }
}