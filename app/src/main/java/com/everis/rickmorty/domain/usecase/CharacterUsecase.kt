package com.everis.rickmorty.domain.usecase

import com.everis.rickmorty.data.repository.CharacterRepository
import com.everis.rickmorty.ui.main.ModelResponse

interface CharacterUseCase {
    fun getCharacters(): ModelResponse?
}

class CharacterUseCaseImpl(private val repository: CharacterRepository) : CharacterUseCase {
    override fun getCharacters(): ModelResponse? {
        return repository.getCharacters()
    }
}