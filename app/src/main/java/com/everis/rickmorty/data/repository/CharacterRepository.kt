package com.everis.rickmorty.data.repository

import com.everis.rickmorty.data.source.CharacterDataSource
import com.everis.rickmorty.ui.main.ModelResponse

interface CharacterRepository {
    fun getCharacters(): ModelResponse?
}

class CharacterRepositoryImpl(dataSource: CharacterDataSource) : CharacterRepository {
    private val remoteDataSource: CharacterDataSource = dataSource
    override fun getCharacters(): ModelResponse? {
        return remoteDataSource.getCharacters()
    }
}