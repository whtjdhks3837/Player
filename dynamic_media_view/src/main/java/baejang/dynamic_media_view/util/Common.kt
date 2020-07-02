package baejang.dynamic_media_view.util

import android.util.Log
import baejang.dynamic_media_view.BuildConfig

object Common {

    fun log(msg: String, tag: String = "LOG") {
        if (BuildConfig.DEBUG) {
            Log.i(tag, "[${Thread.currentThread()}] $msg")
        }
    }
}