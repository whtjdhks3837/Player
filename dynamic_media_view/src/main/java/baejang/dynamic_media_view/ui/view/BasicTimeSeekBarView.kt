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
) : View(context, attrs, defStyleAttr), TimeSeekView, View.OnTouchListener {

    companion object {
        private const val DELAY_MILLIS = 1000L
    }

    private enum class Action {
        HandleMoving, None
    }

    private var action = Action.None

    private val typedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.BasicTimeSeekBarView, 0, 0
    )
    // TODO : Handle resizing
    private val handleBitmap = context getBitmap typedArray.getResourceId(
        R.styleable.BasicTimeSeekBarView_handleSrc,
        R.drawable.ic_circle_24dp
    )
    private val mainBarPaint = Paint().apply {
        color = typedArray.getColor(
            R.styleable.BasicTimeSeekBarView_mainBarColor,
            ContextCompat.getColor(context, R.color.colorWhite)
        )
    }
    private val leftTimeTextPaint = Paint().apply {
        color = typedArray.getColor(
            R.styleable.BasicTimeSeekBarView_timeColor,
            ContextCompat.getColor(context, R.color.colorWhite)
        )
        textSize = 30f
    }
    private val rightTimeTextPaint = Paint().apply {
        color = typedArray.getColor(
            R.styleable.BasicTimeSeekBarView_timeColor,
            ContextCompat.getColor(context, R.color.colorWhite)
        )
        textSize = 30f
    }

    private var player: Player? = null
    private var isStarted: Boolean = false

    private var handleArea: HandleArea? = null

    init {
        setOnTouchListener(this)
    }

    private data class HandleArea(val width: Int, val height: Int, var x: Float, var y: Float) {
        fun getXArea() = (x - (width / 2) - 30..x + (width / 2) + 30)
        fun print() = log("width : $width , height : $height , x : $x , y : $y")
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        // TODO : 멀티 포인터 처리
        when (event.actionMasked) {
            MotionEvent.ACTION_MOVE -> handleArea?.let {
                if (event.x in it.getXArea()) onMoveHandle(event)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                if (action == Action.HandleMoving) onCancelMoveHandle()

        }
        return true
    }

    private fun onMoveHandle(event: MotionEvent) {
        action = Action.HandleMoving
        handleArea?.let {
            it.x = event.x
            it.print()
        }
        invalidate()
    }

    private fun onCancelMoveHandle() {
        action = Action.None
        val handlePer = (handleArea?.x ?: 1f) / width
        player?.let {
            val newPosition = (it.duration * handlePer).toLong()
            it.seekTo(newPosition)
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
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(ContextCompat.getColor(context, R.color.colorGrey))
        drawTimeText(canvas)
        drawMainBar(canvas)
        drawHandle(canvas)
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
        val handleX = when (action) {
            Action.None -> (width * (player?.getPercent() ?: 1f)).apply { handleArea?.x = this }
            Action.HandleMoving -> handleArea?.x ?: 1f
        }
        if (handleArea != null) canvas.drawBitmap(handleBitmap, handleX, handleArea!!.y, null)
        else handleArea = HandleArea(
            handleBitmap.width, handleBitmap.height, handleX, handleBitmap getCenterYFromParent this
        ).apply {
            canvas.drawBitmap(handleBitmap, x, y, null)
        }
        log("cur : ${player?.currentPosition} , max : ${player?.duration}, per : ${handleX / width}")
    }
}