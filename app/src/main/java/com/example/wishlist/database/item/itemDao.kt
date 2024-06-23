package com.example.wishlist.database.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface itemDao {

    @Insert
    fun insertItem(item: ItemData)

    @Delete
    fun deleteItem(item: ItemData)

    @Update
    suspend fun updateItem(item: ItemData)

    @Query("SELECT * FROM item_table ORDER BY itemId DESC")
    suspend fun getAllItemsList(): List<ItemData>

    @Query("SELECT * FROM item_table ORDER BY itemId DESC")
    fun getAllItems(): LiveData<List<ItemData>>

    @Query("SELECT * from item_table WHERE itemId = :key")
    fun get(key : Long) : ItemData?

    @Query("DELETE FROM item_table")
    fun clear()

    @Query("SELECT * FROM item_table WHERE itemId = :itemId")
    fun getItemById(itemId: Long): ItemData?

    @Query("SELECT * FROM item_table ORDER BY itemId DESC LIMIT 1")
    fun getItem() : ItemData?
}