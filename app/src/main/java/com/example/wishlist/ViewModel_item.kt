package com.example.wishlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wishlist.database.item.itemDao
import com.example.wishlist.database.item.ItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class ViewModel_item(
    val database: itemDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelItemJob = Job()
    private val itemuiScope = CoroutineScope(Dispatchers.Main + viewModelItemJob)

    val itemlist = database.getAllItems()


    override fun onCleared() {
        super.onCleared()
        viewModelItemJob.cancel()
    }

    init {
        initializeItem()
    }

    private fun initializeItem() {
        itemuiScope.launch {
            Log.e("ViewModel_item.kt", "Current thread init viewmodel: " + Thread.currentThread().name)
        }
    }

    fun deleteItem(item: ItemData) {
        itemuiScope.launch {
            withContext(Dispatchers.IO) {
                database.deleteItem(item)
            }
        }
    }
}