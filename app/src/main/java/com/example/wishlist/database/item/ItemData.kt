package com.example.wishlist.database.item

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class ItemData(
    @PrimaryKey(autoGenerate = true)
//    var itemId: Long? = 0L,
    var itemId: Long? = null,
    var Naam: String,
    var Bedrag: Double,
    var Url: String,
    var Fabrikant: String,
    var Beschrijving: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(itemId)
        parcel.writeString(Naam)
        parcel.writeDouble(Bedrag)
        parcel.writeString(Url)
        parcel.writeString(Fabrikant)
        parcel.writeString(Beschrijving)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemData> {
        override fun createFromParcel(parcel: Parcel): ItemData {
            return ItemData(parcel)
        }

        override fun newArray(size: Int): Array<ItemData?> {
            return arrayOfNulls(size)
        }
    }
}