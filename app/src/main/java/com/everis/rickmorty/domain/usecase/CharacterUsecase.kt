package com.everis.rickmorty.domain.usecase

import com.everis.rickmorty.data.repository.CharacterRepository
import com.everis.rickmorty.domain.usecase.entity.CharacterEntity
import com.everis.rickmorty.ui.main.ModelResponse

interface CharacterUseCase {
    fun getCharacters(page: Int): ModelResponse?
    fun getCharactersByName(name: String, page: Int): ModelResponse?
    suspend fun insertCharacters(characters: List<CharacterEntity>)
    suspend fun getCharacters()
    suspend fun deleteCharacters()
    suspend fun getSavedCharacters() : List<CharacterEntity>
}

class CharacterUseCaseImpl(private val repository: CharacterRepository) : CharacterUseCase {
    override fun getCharacters(page: Int): ModelResponse? {
        return repository.getCharacters(page)
    }

    override suspend fun getCharacters() {
        repository.getCharacters(1)
    }

    override fun getCharactersByName(name: String, page: Int): ModelResponse? {
        return repository.getCharactersByName(name, page)
    }

    override suspend fun insertCharacters(characters: List<CharacterEntity>) {
        repository.insertCharacters(characters)
    }

    override suspend fun deleteCharacters() {
        repository.deleteCharacters()
    }

    override suspend fun getSavedCharacters() : List<CharacterEntity> {
        return repository.getSavedCharacters()
    }
}