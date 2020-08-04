package br.com.renansantiago.presentation.ui.extensions

import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Picasso

fun AppCompatImageView.loadImageUrl(imageUrl: String) {
    try {
        Picasso.get()
            .load(imageUrl)
            .into(this)
    } catch (ex: Exception) {
    }
}