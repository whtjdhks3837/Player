package baejang.dynamic_media_view.ui.view.player_controller

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.*
import androidx.core.content.ContextCompat
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.ui.view.Area
import baejang.dynamic_media_view.ui.view.AudioControllerView
import baejang.dynamic_media_view.ui.view.VideoControllerView
import baejang.dynamic_media_view.ui.view.ViewHideHelper
import baejang.dynamic_media_view.util.getCenterYFromParent
import baejang.dynamic_media_view.util.isPlaying
import baejang.dynamic_media_view.util.toast
import com.google.android.exoplayer2.Player

class BasicPlayerControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BasePlayerControllerView(context, attrs, defStyleAttr),
    VideoControllerView, AudioControllerView {

    companion object {
        private const val MIN_HEIGHT_PX = 200
        private const val MIN_WIDTH_PX = 1000
    }

    private val paintBundle = PaintBundle(context, attrs)
    private var playArea: Area? = null
    private var pauseArea: Area? = null
    private var previousArea: Area? = null
    private var nextArea: Area? = null

    private val hideHelper = ViewHideHelper(
        this, typedArray.getBoolean(R.styleable.BasePlayerControllerView_auto_hide, false)
    )

    init {
        setOnTouchListener(this)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            when {
                isTouchedArea(playArea, event) -> onTouchPlayAndPause()
                isTouchedArea(pauseArea, event) -> onTouchPlayAndPause()
                isTouchedArea(previousArea, event) -> onTouchPrevious()
                isTouchedArea(nextArea, event) -> onTouchNext()
            }
        }
        return true
    }

    private fun isTouchedArea(area: Area?, event: MotionEvent): Boolean {
        if (area == null) return false
        return event.x in area.getXArea() && event.y in area.getYArea()
    }

    private fun onTouchPlayAndPause() {
        val isPlaying = _player?.isPlaying() ?: false
        if (isPlaying) pause() else play()
    }

    private fun onTouchPrevious() {
        if (!previous()) context toast "처음 영상입니다."
    }

    private fun onTouchNext() {
        if (!next()) context toast "마지막 영상입니다."
    }

    override fun clickOnParent() {
        if (hideHelper.isHide()) hideHelper.show()
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        invalidate()
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
        canvas.drawColor(ContextCompat.getColor(context, R.color.colorAccent))
        drawPrevious(canvas)
        drawPlayAndPause(canvas)
        drawNext(canvas)
    }

    private fun drawPrevious(canvas: Canvas) {
        val previousBitmap = paintBundle.previousBitmap
        if (previousArea == null) previousArea = Area(
            previousBitmap.width,
            previousBitmap.height,
            width * 0.2f,
            previousBitmap getCenterYFromParent this
        )
        canvas.drawBitmap(previousBitmap, previousArea!!.x, previousArea!!.y, null)
    }

    private fun drawPlayAndPause(canvas: Canvas) {
        val playBitmap = paintBundle.playBitmap
        val pauseBitmap = paintBundle.pauseBitmap
        if (playArea == null) playArea = Area(
            playBitmap.width,
            playBitmap.height,
            width * 0.4f,
            playBitmap getCenterYFromParent this
        )
        if (pauseArea == null) pauseArea = Area(
            pauseBitmap.width,
            pauseBitmap.height,
            width * 0.4f,
            pauseBitmap getCenterYFromParent this
        )
        val isPlaying = _player?.isPlaying() ?: false
        if (isPlaying) when (_player?.playbackState) {
            Player.STATE_READY -> canvas.drawBitmap(pauseBitmap, pauseArea!!.x, pauseArea!!.y, null)
            else -> canvas.drawBitmap(playBitmap, playArea!!.x, playArea!!.y, null)
        }
        else canvas.drawBitmap(playBitmap, playArea!!.x, playArea!!.y, null)
    }

    private fun drawNext(canvas: Canvas) {
        val nextBitmap = paintBundle.nextBitmap
        if (nextArea == null) nextArea = Area(
            nextBitmap.width,
            nextBitmap.height,
            width * 0.6f,
            nextBitmap getCenterYFromParent this
        )
        canvas.drawBitmap(paintBundle.nextBitmap, nextArea!!.x, nextArea!!.y, null)
    }
}