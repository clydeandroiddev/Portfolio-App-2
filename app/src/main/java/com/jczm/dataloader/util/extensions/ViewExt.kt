package com.jczm.dataloader.util.extensions

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.onLayoutLoadingComplete(action: () -> Unit) {
    val globalLayoutListener = object: ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            Handler(Looper.getMainLooper()).postDelayed({
                action()
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }, 1000)
        }
    }
    viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}