package com.example.wishlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wishlist.addItem.ViewModel_addItem
import com.example.wishlist.changeItem.ViewModel_changeItem
import com.example.wishlist.database.item.itemDao
import com.example.wishlist.detailItem.ViewModel_detailItem
import com.example.wishlist.database.item.ItemData

class ViewModel_Factory(
    private val dataSource: itemDao,
    private val application: Application,
    private val itemId: Long? = null
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModel_addItem::class.java)) {
            return ViewModel_addItem(dataSource, application) as T
        }
        else if (modelClass.isAssignableFrom(ViewModel_item::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModel_item(dataSource, application) as T
        }
        else if (modelClass.isAssignableFrom(ViewModel_detailItem::class.java)) {
            return itemId?.let {
                ViewModel_detailItem(it, dataSource, application) as T
            } ?: throw IllegalArgumentException("ItemData is required for ViewModel_detailItem")
        }
        else if (modelClass.isAssignableFrom(ViewModel_changeItem::class.java)) {
            return itemId?.let {
                ViewModel_changeItem(dataSource, it) as T
            } ?: throw IllegalArgumentException("ItemData is required for ViewModel_changeItem")
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//class ViewModel_Factory(
//    private val dataSource: itemDao,
//    private val application: Application,
//    private val itemId: Long?
//) : ViewModelProvider.Factory {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ViewModel_addItem::class.java)) {
//            return ViewModel_addItem(dataSource, application) as T
//        } else if (modelClass.isAssignableFrom(ViewModel_detailItem::class.java)) {
//            return ViewModel_detailItem(itemId, dataSource, application) as T
//        } else if (modelClass.isAssignableFrom(ViewModel_changeItem::class.java)) {
////            return ViewModel_changeItem(dataSource, itemId) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
