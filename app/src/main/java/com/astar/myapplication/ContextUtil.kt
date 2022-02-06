package com.astar.myapplication

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build


const val INTENT_REQUEST_CODE = 3902

fun Context.newBroadcastIntent(what: String): PendingIntent {
    return PendingIntent.getBroadcast(
        this, INTENT_REQUEST_CODE, Intent(what),
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_IMMUTABLE
        else 0
    )
}