package com.everis.rickmorty.presentation.modules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everis.rickmorty.domain.usecase.CharacterUseCase
import com.everis.rickmorty.ui.main.ModelResponse
import com.everis.rickmorty.ui.main.Results

class MainViewModel(private val usecase: CharacterUseCase) : ViewModel() {

    private val _userProfileResponse = MutableLiveData<ModelResponse>()
    val userProfileResponse: LiveData<ModelResponse> = _userProfileResponse

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun getCharacters() {
        val userProfileResponse = usecase.getCharacters()

        userProfileResponse?.let {
            _userProfileResponse.value = it
        } ?: run {
            _error .value = true
        }
    }
    fun filterCharacters(s: String): ArrayList<Results> {
        val filteredList = ArrayList<Results>()

        userProfileResponse.value?.results?.let { results ->
            for (item in results) {
                if (item.name?.contains(s, ignoreCase = true) == true) {
                    filteredList.add(item)
                }
            }
        }

        return filteredList
    }
}