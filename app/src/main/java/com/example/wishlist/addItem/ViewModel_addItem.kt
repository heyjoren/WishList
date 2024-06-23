package com.example.wishlist.addItem

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wishlist.MainActivity
import com.example.wishlist.add_item
import com.example.wishlist.database.item.itemDao
import com.example.wishlist.database.item.ItemData
import com.example.wishlist.item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel_addItem(
    val database: itemDao,
    application: Application) :
    AndroidViewModel(application) {

    val naam = MutableLiveData<String>()
    val bedrag = MutableLiveData<String>()
    val fabrikant = MutableLiveData<String>()
    val url = MutableLiveData<String>()
    val beschrijving = MutableLiveData<String>()

    private val _navigateToPreviousFragment = MutableLiveData<Boolean>()
    val navigateToPreviousFragment: LiveData<Boolean>
        get() = _navigateToPreviousFragment

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var item = MutableLiveData<ItemData?>()
    private val items = database.getAllItems()

    init {
        initializeItem()
    }

    private fun initializeItem() {
        uiScope.launch {
            item.value = getItemFromDatabase()
        }
    }

    private suspend fun getItemFromDatabase() : ItemData? {
        return withContext(Dispatchers.IO) {
            var item = database.getItem()
            item
        }
    }

    fun onAdd() {
        val item = ItemData(
            Naam = naam.value ?: "",
            Bedrag = bedrag.value?.toDoubleOrNull() ?: 0.0,
            Url = url.value ?: "",
            Fabrikant = fabrikant.value ?: "",
            Beschrijving = beschrijving.value ?: ""
        )
        uiScope.launch {
            insertItem(item)
        }
        _navigateToPreviousFragment.value = true
    }

    private suspend fun insertItem(item: ItemData) {
        withContext(Dispatchers.IO) {
            database.insertItem(item)
            Log.d("testResyclerviewer_viewmodel_addItem.kt", "Current thread add viewmodel: " + Thread.currentThread().name)
        }
    }

    fun doneNavigating() {
        _navigateToPreviousFragment.value = false
    }
}