package baejang.dynamic_media_view.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.*
import androidx.core.content.ContextCompat
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.util.*
import com.google.android.exoplayer2.Player

class BasicTimeSeekBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), TimeSeekView {

    companion object {
        private const val DELAY_MILLIS = 1000L
    }

    // TODO : Attribute 만들기
    private val handleBitmap = context getBitmap R.drawable.ic_android_24dp
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

    private var player: Player? = null
    private var isStarted: Boolean = false

    private var handleX = 0f

    init {
        setOnTouchListener { _, motionEvent ->
            if (motionEvent.x in handleX - handleBitmap.width..handleX + handleBitmap.width)
                log("악!")
            else log("힝..")
            true
        }
    }

    override fun setPlayer(player: Player) {
        if (this.player != null) return
        this.player = player
    }

    override fun start() {
        if (isStarted) return
        isStarted = true
        player?.addListener(this)
        update()
    }

    override fun stop() {
        if (!isStarted) return
        isStarted = false
        player?.removeListener(this)
        removeCallbacks(this)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        update()
    }

    override fun onPositionDiscontinuity(reason: Int) {
        update()
    }

    override fun run() {
        update()
    }

    private fun update() {
        log("current: ${player?.currentPosition}")
        invalidate()
        removeCallbacks(this)
        postDelayed(this, DELAY_MILLIS)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // TODO : 가로세로, DP 구하기
        val width = when (val w = getMode(widthMeasureSpec)) {
            EXACTLY -> if (w <= 600) 600 else getMode(widthMeasureSpec)
            AT_MOST -> 600
            else -> getSize(widthMeasureSpec)
        }
        val height = when (getMode(heightMeasureSpec)) {
            EXACTLY, AT_MOST -> 200
            else -> getSize(heightMeasureSpec)
        }
        setMeasuredDimension(width, height)
        log("Custom view density ${resources.displayMetrics.density}")
        log("onMeasure : ${getSize(widthMeasureSpec)} , ${getSize(heightMeasureSpec)}")
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(ContextCompat.getColor(context, R.color.colorGrey))
        drawTimeText(canvas)
        drawMainBar(canvas)
        drawHandle(canvas)
        log("width $width , height $height")
    }

    private fun drawTimeText(canvas: Canvas) {
        val textY = (height / 2).toFloat() - 60
        canvas.drawText(player?.getPlayTime() ?: "00:00", 10f, textY, leftTimeTextPaint)
        canvas.drawText(player?.getMaxTime() ?: "00:00", width - 100f, textY, rightTimeTextPaint)
    }

    private fun drawMainBar(canvas: Canvas) {
        val centerY = (height / 2).toFloat()
        val rect = RectF(20f, (centerY - 10), (width - 20).toFloat(), (centerY + 10))
        canvas.drawRoundRect(rect, 20f, 20f, mainBarPaint)
    }

    private fun drawHandle(canvas: Canvas) {
        val per = player?.getPercent() ?: 1f
        val handleY = handleBitmap getCenterYFromParent this
        val position = width * per
        handleX = position
        log("cur : ${player?.currentPosition} , max : ${player?.duration}, per : $per")
        canvas.drawBitmap(handleBitmap, position, handleY, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        log("real width $w , height $h")
    }
}