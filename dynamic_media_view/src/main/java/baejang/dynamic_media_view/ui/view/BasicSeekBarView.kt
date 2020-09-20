package baejang.dynamic_media_view.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.util.getBitmap
import baejang.dynamic_media_view.util.log

class BasicSeekBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val handleBitmap = getBitmap(context, R.drawable.ic_android_24dp)
    private val mainBarPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.colorWhite)
    }
    private val leftTimeTextPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.colorWhite)
        textSize = 30f
    }
    private val rightTimeTextPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.colorWhite)
        textSize = 30f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        log("Custom view density ${resources.displayMetrics.density}")
        log("onMeasure : "
                + "${MeasureSpec.getSize(widthMeasureSpec)} ,"
                + " ${MeasureSpec.getSize(heightMeasureSpec)}"
        )
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val centerY = (height / 2).toFloat()
        val handleY = centerY - (handleBitmap.height / 2)
        val textY = centerY - 60

        canvas.drawColor(ContextCompat.getColor(context, R.color.colorGrey))
        canvas.drawText("00:00", 10f, textY, leftTimeTextPaint)
        canvas.drawText("00:00", width - 100f, textY, rightTimeTextPaint)
        val rect = RectF(20f, (centerY - 10), (width - 20).toFloat(), (centerY + 10))
        canvas.drawRoundRect(rect, 20f, 20f, mainBarPaint)
        canvas.drawBitmap(handleBitmap, 0f, handleY, null)
        log("width $width , height $height")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        log("real width $w , height $h")
    }
}