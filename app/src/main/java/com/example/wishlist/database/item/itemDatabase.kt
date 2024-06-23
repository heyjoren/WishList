package com.example.wishlist.database.item

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ItemData::class],
    version = 1
)
abstract class itemDatabase : RoomDatabase() {
    abstract val itemDao : itemDao

    companion object {
        @Volatile
        private var INSTANCE: itemDatabase? = null

        fun getInstance(context: Context): itemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    itemDatabase::class.java,
                    "item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}