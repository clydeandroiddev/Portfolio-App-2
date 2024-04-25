package com.jczm.dataloader.util.extensions

import android.content.Context

fun Context.readJsonFromAssets(fileName : String) : String {
    return assets.open(fileName).bufferedReader().use { it.readText() }
}