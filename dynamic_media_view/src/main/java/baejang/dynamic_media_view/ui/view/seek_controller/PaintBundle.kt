package baejang.dynamic_media_view.ui.view.seek_controller

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.util.getBitmap
import baejang.dynamic_media_view.util.resize

class PaintBundle(private val context: Context, private val attrs: AttributeSet? = null) {

    private val typedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.TimeSeekView, 0, 0
    )

    val handleBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.TimeSeekView_handle_src,
            R.drawable.ic_circle_24dp
        )
    ).resize(context, 24)

    val mainBarPaint = Paint().apply {
        color = typedArray.getColor(
            R.styleable.TimeSeekView_main_bar_color,
            ContextCompat.getColor(context, R.color.colorWhite)
        )
    }

    val leftTimeTextPaint = Paint().apply {
        color = typedArray.getColor(
            R.styleable.TimeSeekView_time_color,
            ContextCompat.getColor(context, R.color.colorWhite)
        )
        textSize = 30f
    }

    val rightTimeTextPaint = Paint().apply {
        color = typedArray.getColor(
            R.styleable.TimeSeekView_time_color,
            ContextCompat.getColor(context, R.color.colorWhite)
        )
        textSize = 30f
    }
}