package com.everis.rickmorty.data.repository

import androidx.room.Room
import com.everis.rickmorty.data.database.CharacterDatabase
import com.everis.rickmorty.data.source.CharacterDataSource
import com.everis.rickmorty.domain.usecase.entity.CharacterEntity
import com.everis.rickmorty.ui.main.ModelResponse

interface CharacterRepository {
    fun getCharacters(page: Int): ModelResponse?
    fun getCharactersByName(name: String, page: Int): ModelResponse?
    suspend fun insertCharacters(characters: List<CharacterEntity>)
    suspend fun deleteCharacters()
    suspend fun getSavedCharacters() : List<CharacterEntity>
}

class CharacterRepositoryImpl(dataSource: CharacterDataSource) : CharacterRepository {
    private val remoteDataSource: CharacterDataSource = dataSource

    override fun getCharacters(page: Int): ModelResponse? {
        return remoteDataSource.getCharacters(page)
    }

    override fun getCharactersByName(name: String, page: Int): ModelResponse? {
        return remoteDataSource.getCharactersByName(name, page)
    }

    override suspend fun insertCharacters(characters: List<CharacterEntity>) {
        remoteDataSource.insertCharacters(characters)
    }

    override suspend fun deleteCharacters() {
        remoteDataSource.deleteCharacters()
    }

    override suspend fun getSavedCharacters(): List<CharacterEntity> {
        return remoteDataSource.getSavedCharacters()
    }
}