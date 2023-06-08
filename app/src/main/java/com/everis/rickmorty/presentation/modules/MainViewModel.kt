package com.everis.rickmorty.presentation.modules

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.everis.rickmorty.data.database.CharacterDatabase
import com.everis.rickmorty.domain.usecase.CharacterUseCase
import com.everis.rickmorty.domain.usecase.entity.CharacterEntity
import com.everis.rickmorty.ui.main.ModelResponse
import com.everis.rickmorty.ui.main.Results
import kotlinx.coroutines.launch

class MainViewModel(private val usecase: CharacterUseCase) : ViewModel() {
    private val _userProfileResponse = MutableLiveData<ModelResponse>()
    val userProfileResponse: LiveData<ModelResponse> = _userProfileResponse

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private var lastNameUsed = ""

    var pages: Int = 0
    var currentPage: Int = 0


    fun getCharacters(page: Int, updatePage: Boolean = false) {
        val userProfileResponse = usecase.getCharacters(page)

        userProfileResponse?.let {
            lastNameUsed = ""

            if (updatePage) {
                it.info?.pages?.let { numPages ->
                    pages = numPages
                    currentPage = 1
                }
            }
            _userProfileResponse.value = it

            saveData(it.results)
        } ?: run {
            _error.value = true
        }
    }

    fun getCharactersByName(name: String, page: Int, updatePage: Boolean = false) {
        lastNameUsed = name
        val userProfileResponse = usecase.getCharactersByName(name, page)

        userProfileResponse?.let {

            if (updatePage) {
                it.info?.pages?.let { numPages ->
                    pages = numPages
                    currentPage = 1
                }
            }
            _error.value = false
            _userProfileResponse.value = it
        } ?: run {
            _error.value = true
        }
    }

    fun RightArrowClick() {

        if (currentPage < pages) {
            currentPage++

            if (lastNameUsed != "") {
                getCharactersByName(lastNameUsed, currentPage)

            } else {
                getCharacters(currentPage)
            }
        }
    }

    fun LeftArrowClick() {
        if (currentPage > 1) {
            currentPage--
            if (lastNameUsed != "") {
                getCharactersByName(lastNameUsed, currentPage)

            } else {
                getCharacters(currentPage)
            }
        }
    }

    fun saveData(l: ArrayList<Results>) {
        var list: MutableList<CharacterEntity> = mutableListOf()
        l.forEach {
            list.add(
                CharacterEntity(
                    id = it.id,
                    name = it.name,
                    status = it.status,
                    species = it.species,
                    type = it.type,
                    gender = it.gender,
                    origin = it.origin,
                    location = it.location,
                    image = it.image,
                    episode = it.episode,
                    url = it.url,
                    created = it.created
                )
            )
        }

        viewModelScope.launch {
            usecase.deleteCharacters()
            usecase.insertCharacters(list.toList())
            val savedList = usecase.getSavedCharacters()
            println("SAVED CHARACTERS  :: "+savedList.size +" :: "+savedList)
        }
    }
}