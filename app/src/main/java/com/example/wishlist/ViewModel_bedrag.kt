package com.example.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ViewModel_bedrag : ViewModel(){
    private val _welcomeMessage = MutableLiveData<String>()
    val welcomeMessage: LiveData<String> = _welcomeMessage

    init {
        _welcomeMessage.value = "Dit is de bedrag pagina"
    }

    fun updateWelcomeMessage(newMessage: String) {
        _welcomeMessage.value = newMessage
    }
}