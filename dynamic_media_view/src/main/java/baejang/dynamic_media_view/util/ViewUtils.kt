package baejang.dynamic_media_view.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

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

infix fun Bitmap.getCenterYFrom(view: View): Float {
    return (view.height / 2).toFloat() - (height / 2)
}

fun Bitmap.resize(context: Context, dp: Int): Bitmap {
    return resize(context, dp, dp)
}

fun Bitmap.resize(context: Context, widthDp: Int, heightDp: Int): Bitmap {
    return if (context getDp width == widthDp || context getDp height == heightDp) this
    else Bitmap.createScaledBitmap(this, context getPixel widthDp, context getPixel heightDp, false)
}

infix fun Context.getDp(pixel: Int): Int {
    val dpi = resources.displayMetrics.density
    return (pixel / dpi).toInt()
}

infix fun View.getDp(pixel: Int): Int {
    return context getDp pixel
}

infix fun Context.getPixel(dp: Int): Int {
    val dpi = resources.displayMetrics.density
    return (dp * dpi).toInt()
}

infix fun View.getPixel(dp: Int): Int {
    return context getPixel dp
}

infix fun Context.getDrawable(@DrawableRes resId: Int) = ContextCompat.getDrawable(this, resId)