package baejang.dynamic_media_view.util

import android.content.Context
import android.util.Log
import android.widget.Toast

infix fun Any.log(msg: String) = Log.i(this::class.java.simpleName, msg)

infix fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

infix fun Context.toast(resource: Int) = toast(getString(resource))