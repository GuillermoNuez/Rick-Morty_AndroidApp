package com.everis.rickmorty.data.source

import com.everis.rickmorty.ui.main.ModelResponse
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

interface CharacterDataSource {
    fun getCharacters(): ModelResponse?
}

class CharacterDataSourceImpl : CharacterDataSource {
    override fun getCharacters(): ModelResponse? {
        val result = runBlocking(Dispatchers.IO) {
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
                    return@runBlocking null
                }

                val jsonStringHolder = StringBuilder()

                BufferedReader(InputStreamReader(httpURLConnection.inputStream)).use { reader ->
                    jsonStringHolder.append(reader.readText())
                }

                val result = jsonStringHolder.toString()
                if (result.isNotEmpty()) {
                    Gson().fromJson(result, ModelResponse::class.java)
                } else {
                    null
                }
            } catch (ioexception: IOException) {
                "ERROR: Something went wrong"
                null
            } finally {
                httpURLConnection?.disconnect()
            }
        }
        return result
    }
}