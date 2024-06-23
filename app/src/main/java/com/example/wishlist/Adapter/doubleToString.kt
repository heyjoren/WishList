package com.example.wishlist.Adapter

import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("app:doubleToString")
fun doubleToString(view: EditText, value: Double?) {
    view.setText(value?.toString() ?: "")
}