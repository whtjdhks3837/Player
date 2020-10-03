package baejang.dynamic_media_view.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import baejang.dynamic_media_view.BuildConfig

fun Any.log(msg: String, tag: String? = null) {
    if (BuildConfig.DEBUG)
        Log.i(tag ?: this::class.java.simpleName, "[${Thread.currentThread()}] $msg")
}

infix fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

infix fun Context.toast(resource: Int) = toast(getString(resource))