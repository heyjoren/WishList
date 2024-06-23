package com.example.wishlist.Adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("truncatedText")
fun setTruncatedText(textView: TextView, text: String?) {
    text?.let {
        textView.text = if (it.length > 22) {
            "${it.substring(0, 19)}..."
        } else {
            it
        }
    }
}
