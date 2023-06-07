package com.everis.rickmorty.data.repository

import com.everis.rickmorty.data.source.CharacterDataSource
import com.everis.rickmorty.ui.main.ModelResponse

interface CharacterRepository {
    fun getCharacters(page: Int): ModelResponse?
    fun getCharactersByName(name: String, page: Int): ModelResponse?
}

class CharacterRepositoryImpl(dataSource: CharacterDataSource) : CharacterRepository {
    private val remoteDataSource: CharacterDataSource = dataSource
    override fun getCharacters(page: Int): ModelResponse? {
        return remoteDataSource.getCharacters(page)
    }

    override fun getCharactersByName(name: String, page: Int): ModelResponse? {
        return remoteDataSource.getCharactersByName(name,page)
    }
}