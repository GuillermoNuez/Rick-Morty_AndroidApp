package com.everis.rickmorty.presentation.modules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everis.rickmorty.domain.usecase.CharacterUseCase
import com.everis.rickmorty.ui.main.ModelResponse


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

            if(updatePage) {
                it.info?.pages?.let { numPages ->
                    pages = numPages
                    currentPage = 1
                }
            }

            _userProfileResponse.value = it
        } ?: run {
            _error.value = true
        }
    }

    fun getCharactersByName(name: String, page: Int, updatePage: Boolean = false) {
        lastNameUsed = name
        val userProfileResponse = usecase.getCharactersByName(name, page)

        userProfileResponse?.let {

            if(updatePage) {
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

    fun RightArrowClick () {

        if (currentPage < pages) {
            currentPage++

            if(lastNameUsed!="") {
                getCharactersByName(lastNameUsed,currentPage)

            }else {
                getCharacters(currentPage)
            }
        }
    }

    fun LeftArrowClick () {
        if (currentPage > 1) {
            currentPage--
            if(lastNameUsed!="") {
                getCharactersByName(lastNameUsed,currentPage)

            }else {
                getCharacters(currentPage)
            }
        }
    }
}