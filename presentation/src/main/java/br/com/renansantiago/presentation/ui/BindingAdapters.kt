package br.com.renansantiago.presentation.ui

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import br.com.renansantiago.presentation.ui.extensions.loadImageUrl

/**
 * Created by Renan Santiago on 27/01/19.
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("show")
    fun View.show(show: Boolean) {
        visibility = if (show) VISIBLE else GONE
    }

    @JvmStatic
    @BindingAdapter("loadImageUrl")
    fun AppCompatImageView.loadUrl(imageUrl: String) {
        this.loadImageUrl(imageUrl)
    }
}