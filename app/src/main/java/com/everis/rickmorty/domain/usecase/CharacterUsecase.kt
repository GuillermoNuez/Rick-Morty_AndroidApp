package com.everis.rickmorty.domain.usecase

import com.everis.rickmorty.data.repository.CharacterRepository
import com.everis.rickmorty.ui.main.ModelResponse

interface CharacterUseCase {
    fun getCharacters(page: Int): ModelResponse?
    fun getCharactersByName(name:String, page: Int): ModelResponse?
}

class CharacterUseCaseImpl(private val repository: CharacterRepository) : CharacterUseCase {
    override fun getCharacters(page: Int): ModelResponse? {
        return repository.getCharacters(page)
    }

    override fun getCharactersByName(name: String, page: Int): ModelResponse? {
        return repository.getCharactersByName(name, page)
    }
}