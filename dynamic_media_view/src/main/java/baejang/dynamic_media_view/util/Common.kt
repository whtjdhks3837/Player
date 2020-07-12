package baejang.dynamic_media_view.util

import android.util.Log
import baejang.dynamic_media_view.BuildConfig

fun log(msg: String, tag: String = "DynamicMediaView") {
    if (BuildConfig.DEBUG) {
        Log.i(tag, "[${Thread.currentThread()}] $msg")
    }
}