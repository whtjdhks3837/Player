package baejang.dynamic_media_view.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import java.lang.IllegalArgumentException

infix fun Context.getBitmap(@DrawableRes resId: Int): Bitmap {
    return when (val drawable = ContextCompat.getDrawable(this, resId)) {
        is BitmapDrawable -> drawable.bitmap
        is VectorDrawable -> drawable.getBitmap()
        else -> throw IllegalArgumentException("unsupported")
    }
}

fun VectorDrawable.getBitmap(): Bitmap {
    return Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888).apply {
        val canvas = Canvas(this)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)
    }
}

infix fun Bitmap.getCenterYFromParent(view: View): Float {
    return (view.height / 2).toFloat() - (height / 2)
}