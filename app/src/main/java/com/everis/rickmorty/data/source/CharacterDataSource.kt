package com.everis.rickmorty.data.source

import android.content.Context
import androidx.room.Room
import com.everis.rickmorty.data.database.CharacterDatabase
import com.everis.rickmorty.domain.usecase.entity.CharacterEntity
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
    fun getCharacters(page:Int): ModelResponse?
    fun getCharactersByName(name: String, page:Int): ModelResponse?
    suspend fun insertCharacters(characters: List<CharacterEntity>)
    suspend fun deleteCharacters()
    suspend fun getSavedCharacters(): List<CharacterEntity>

}

class CharacterDataSourceImpl (private val context: Context) : CharacterDataSource {
    var characterDatabase: CharacterDatabase? = Room.databaseBuilder(
        context,
        CharacterDatabase::class.java,
        "character_database"
    ).build()

    override fun getCharacters(page:Int): ModelResponse? {
        val result = runBlocking(Dispatchers.IO) {
            var httpURLConnection: HttpURLConnection? = null
            try {
                val url = URL("https://rickandmortyapi.com/api/character?page=$page")
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

                    println("Data :: $result")
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

    override fun getCharactersByName(name: String,  page:Int): ModelResponse? {
        val result = runBlocking(Dispatchers.IO) {
            var httpURLConnection: HttpURLConnection? = null
            try {
                val url = URL("https://rickandmortyapi.com/api/character/?name=$name&page=$page")
                println("URL :: $url")
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

                    println("Data :: $result")
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

    override suspend fun insertCharacters(characters: List<CharacterEntity>) {
        println("SIZE  :: "+characters.size)
        characterDatabase?.characterDao()?.insertAll(characters)
    }

    override suspend fun deleteCharacters() {
        characterDatabase?.characterDao()?.deleteAll()
    }

    override suspend fun getSavedCharacters(): List<CharacterEntity> {
        return characterDatabase?.characterDao()?.getAll()!!
    }
}