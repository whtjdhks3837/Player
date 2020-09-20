package baejang.dynamic_media_view.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import java.lang.IllegalArgumentException

fun getBitmap(context: Context, @DrawableRes resId: Int): Bitmap {
    return when (val drawable = ContextCompat.getDrawable(context, resId)) {
        is BitmapDrawable -> drawable.bitmap
        is VectorDrawable -> getBitmap(drawable)
        else -> throw IllegalArgumentException("unsupported")
    }
}

fun getBitmap(vectorDrawable: VectorDrawable): Bitmap {
    return Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    ).apply {
        val canvas = Canvas(this)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
    }
}