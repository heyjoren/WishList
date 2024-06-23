package com.example.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Viewmodel_home : ViewModel(){
    private val _welcomeMessage = MutableLiveData<String>()
    val welcomeMessage: LiveData<String> = _welcomeMessage

    init {
        _welcomeMessage.value = "My Wishlist"
    }

}