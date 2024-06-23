package com.example.wishlist

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class TextIconAdapter(context: Context, private val items: Array<String>, private val icons: Array<Int>) :
    ArrayAdapter<String>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.alert_dialog_text_icon, parent, false)

        val textView = view.findViewById<TextView>(R.id.text)
        val imageView = view.findViewById<ImageView>(R.id.icon)

        textView.text = items[position]
        imageView.setImageResource(icons[position])

        return view
    }
}
