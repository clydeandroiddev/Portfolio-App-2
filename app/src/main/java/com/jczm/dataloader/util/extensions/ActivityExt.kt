package com.jczm.dataloader.util.extensions

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build

fun Activity.recreateSmoothly() {
    finish()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN,android.R.anim.fade_in, android.R.anim.slide_out_right,Color.WHITE)
    }else {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right)
    }
    startActivity(Intent(this.intent))
}