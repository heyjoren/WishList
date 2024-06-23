package com.example.wishlist.changeItem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlist.database.item.ItemData
import com.example.wishlist.database.item.itemDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel_changeItem(
    val database: itemDao,
    private val itemId: Long
) : ViewModel() {
    val naam = MutableLiveData<String>()
    val bedrag = MutableLiveData<String>()
    val fabrikant = MutableLiveData<String>()
    val url = MutableLiveData<String>()
    val beschrijving = MutableLiveData<String>()

    private val _selectedItem = MutableLiveData<ItemData>()
    val selectedItem: LiveData<ItemData>
        get() = _selectedItem

    private val _navigateToPreviousFragment = MutableLiveData<Boolean>()
    val navigateToPreviousFragment: LiveData<Boolean>
        get() = _navigateToPreviousFragment

    init {
        viewModelScope.launch {
            _selectedItem.value = getItemFromDatabase()
            _selectedItem.value?.let {
                naam.value = it.Naam
                bedrag.value = it.Bedrag.toString()
                fabrikant.value = it.Fabrikant
                url.value = it.Url
                beschrijving.value = it.Beschrijving
            }
        }
    }

    private suspend fun getItemFromDatabase(): ItemData? {
        return withContext(Dispatchers.IO) {
            database.getItemById(itemId)
        }
    }

    fun onUpdate() {
        val item = ItemData(
            itemId = _selectedItem.value?.itemId,
            Naam = naam.value ?: "",
            Bedrag = bedrag.value?.toDoubleOrNull() ?: 0.0,
            Url = url.value ?: "",
            Fabrikant = fabrikant.value ?: "",
            Beschrijving = beschrijving.value ?: ""
        )
        viewModelScope.launch {
            updateItem(item)
            _navigateToPreviousFragment.value = true
        }
    }

    private suspend fun updateItem(item: ItemData) {
        withContext(Dispatchers.IO) {
            database.updateItem(item)
            Log.d("viewmodel_changeItem.kt", "Current thread change viewmodel: " + Thread.currentThread().name)
        }
    }

    fun doneNavigating() {
        _navigateToPreviousFragment.value = false
    }

}