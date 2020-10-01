package baejang.dynamic_media_view.ui.view.seek

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.*
import androidx.core.content.ContextCompat
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.ui.view.Area
import baejang.dynamic_media_view.ui.view.TimeSeekView
import baejang.dynamic_media_view.ui.view.ViewHideHelper
import baejang.dynamic_media_view.util.*

class BasicTimeSeekBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseTimeSeekView(context, attrs, defStyleAttr), TimeSeekView {

    companion object {
        private const val DELAY_MILLIS = 1000L
        private const val MIN_HEIGHT_PX = 200
        private const val MIN_WIDTH_PX = 600
    }

    private enum class Action { HandleMoving, None }

    private var action = Action.None
    private var handleArea: Area? = null
    private val paintBundle = PaintBundle(context, attrs)
    private val hideHelper = ViewHideHelper(
        this, typedArray.getBoolean(R.styleable.BaseTimeSeekView_auto_hide, false)
    )

    init {
        setOnTouchListener(this)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        // TODO : 멀티 포인터 처리
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> handleArea?.let {
                if (event.x in it.getXArea() && event.y in it.getYArea())
                    action = Action.HandleMoving
            }
            MotionEvent.ACTION_MOVE ->
                if (action == Action.HandleMoving) onMoveHandle(event)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                if (action == Action.HandleMoving) onCancelMoveHandle()
        }
        return true
    }

    private fun onMoveHandle(event: MotionEvent) {
        handleArea?.x = event.x
        handleArea?.print()
        invalidate()
    }

    private fun onCancelMoveHandle() {
        action = Action.None
        _player?.let {
            val handlePer = (handleArea?.x ?: 1f) / width
            val newPosition = (it.duration * handlePer).toLong()
            it.seekTo(newPosition)
        }
    }

    override fun clickOnParent() {
        if (hideHelper.isHide()) hideHelper.show()
    }

    override fun update() {
        if (_player?.isPlaying() == true) invalidate()
        removeCallbacks(this)
        postDelayed(this, DELAY_MILLIS)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = when (val w = getMode(widthMeasureSpec)) {
            EXACTLY -> if (w <= MIN_WIDTH_PX) MIN_WIDTH_PX else getMode(widthMeasureSpec)
            AT_MOST -> MIN_WIDTH_PX
            else -> getSize(widthMeasureSpec)
        }
        val height = when (getMode(heightMeasureSpec)) {
            EXACTLY, AT_MOST -> MIN_HEIGHT_PX
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
        val leftTimeTextPaint = paintBundle.leftTimeTextPaint
        val rightTimeTextPaint = paintBundle.rightTimeTextPaint
        canvas.drawText(_player?.getPlayTime() ?: "00:00", 10f, textY, leftTimeTextPaint)
        canvas.drawText(_player?.getMaxTime() ?: "00:00", width - 100f, textY, rightTimeTextPaint)
    }

    private fun drawMainBar(canvas: Canvas) {
        val centerY = (height / 2).toFloat()
        val rect = RectF(20f, (centerY - 10), (width - 20).toFloat(), (centerY + 10))
        canvas.drawRoundRect(rect, 20f, 20f, paintBundle.mainBarPaint)
    }

    private fun drawHandle(canvas: Canvas) {
        val handleX = when (action) {
            Action.None -> (width * (_player?.getPercent() ?: 1f)).apply { handleArea?.x = this }
            Action.HandleMoving -> handleArea?.x ?: 1f
        }
        val handleBitmap = paintBundle.handleBitmap
        if (handleArea != null) canvas.drawBitmap(handleBitmap, handleX, handleArea!!.y, null)
        else handleArea = Area(
            handleBitmap.width, handleBitmap.height, handleX, handleBitmap getCenterYFromParent this
        ).apply {
            canvas.drawBitmap(handleBitmap, x, y, null)
        }
        log("cur : ${_player?.currentPosition} , max : ${_player?.duration}, per : ${handleX / width}")
    }
}