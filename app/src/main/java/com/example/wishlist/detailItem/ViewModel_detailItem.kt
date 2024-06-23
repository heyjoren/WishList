package com.example.wishlist.detailItem

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wishlist.database.item.ItemData
import com.example.wishlist.database.item.itemDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel_detailItem(
    private val itemId: Long,
    val database: itemDao,
    application: Application) : AndroidViewModel(application) {

    private val _item = MutableLiveData<ItemData>()
    val item: LiveData<ItemData>
        get() = _item

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        initializeItem()
    }

    private fun initializeItem() {
        uiScope.launch {
            _item.value = getItemFromDatabase()
        }
    }

    private suspend fun getItemFromDatabase(): ItemData? {
        return withContext(Dispatchers.IO) {
            database.getItemById(itemId)
        }
    }

    fun isFieldEmpty(field: String?): Boolean {
        return field.isNullOrEmpty()
    }

    fun isDoubleFieldEmpty(field: Double?): Boolean {
        return field == null || field == 0.0
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

