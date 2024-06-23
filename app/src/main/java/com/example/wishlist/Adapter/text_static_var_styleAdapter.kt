package com.example.wishlist.Adapter

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.wishlist.R

@BindingAdapter("text_static_var_style_StaticTekst", "text_static_var_style_VarTekst")
//class text_static_var_style {}
fun text_static_var_style(view: TextView, text_static_var_style_StaticTekst:String?, text_static_var_style_VarTekst: Any?) {
    if (text_static_var_style_VarTekst != null) {
        val staticText = text_static_var_style_StaticTekst
        val spannable = SpannableString(staticText + text_static_var_style_VarTekst)
        val darkBlueColor = view.context.resources.getColor(R.color.dark_blue, null)

        if (staticText != null) {
            spannable.setSpan(
                ForegroundColorSpan(darkBlueColor),
                staticText.length,
                spannable.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            (staticText ?: "").length,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        view.text = spannable
    } else {
        view.text = text_static_var_style_StaticTekst ?: ""
    }
}