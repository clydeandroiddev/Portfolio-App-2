package com.jczm.dataloader.util.helper

import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jczm.dataloader.R
import java.lang.StringBuilder
import java.text.DecimalFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

@BindingAdapter("app:imageSrc")
fun ImageView.loadImage(imageSrc: String) {
    Glide.with(this.context).load(imageSrc)
        .transform(CenterCrop(), RoundedCorners(12))
        .placeholder(R.drawable.background_placeholder)
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

@BindingAdapter("app:price", "app:currency", requireAll = false)
fun TextView.setPrice(price: Int, currency: String) {

    val decimalFormat = DecimalFormat("#,###")
    val formattedPrice = decimalFormat.format(price)
    val spannedStringBuilder = SpannableStringBuilder()
        .append(formattedPrice)
        .append(" ")
        .append(currency)

    text = spannedStringBuilder.toString()
}

@BindingAdapter("app:endTime")
fun TextView.setEndTime(endTime: Long) {
    val day = TimeUnit.SECONDS.toDays(endTime)
    val hours: Long = TimeUnit.SECONDS.toHours(endTime) - day * 24
    val minute: Long = TimeUnit.SECONDS.toMinutes(endTime) - TimeUnit.SECONDS.toHours(endTime) * 60
    val endTimeStr = StringBuilder()
    val language = Locale.getDefault().language
    if (language.equals("ar")) {
        endTimeStr.append("m ")
        endTimeStr.append(String.format("%02d", minute))
        if (hours > 0) {
            endTimeStr.append(" : ")
            endTimeStr.append("h ")
            endTimeStr.append(String.format("%02d", hours))
        }
        if (day > 0) {
            endTimeStr.append(" : ")
            endTimeStr.append("d ")
            endTimeStr.append(String.format("%02d", day))
        }
    } else {
        if (day > 0) {
            endTimeStr.append(String.format("%02d", day))
            endTimeStr.append("d ")
            endTimeStr.append(": ")
        }
        if (hours > 0) {
            endTimeStr.append(String.format("%02d", hours))
            endTimeStr.append("h ")
            endTimeStr.append(": ")
        }
        endTimeStr.append(String.format("%02d", minute))
        endTimeStr.append("m ")

    }


    text = endTimeStr
}