package baejang.dynamic_media_view.ui.view.media_controller

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.*
import androidx.core.content.ContextCompat
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.ui.view.Area
import baejang.dynamic_media_view.util.getCenterYFrom
import baejang.dynamic_media_view.util.isPlaying
import baejang.dynamic_media_view.util.toast
import com.google.android.exoplayer2.Player

class BasicMediaControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MediaControllerView(context, attrs, defStyleAttr) {

    companion object {
        private const val MIN_HEIGHT_PX = 150
        private const val MIN_WIDTH_PX = 1000
    }

    private val paintBundle = PaintBundle(context, attrs)
    private var playArea: Area? = null
    private var pauseArea: Area? = null
    private var previousArea: Area? = null
    private var nextArea: Area? = null
    private var repeatArea: Area? = null
    private var shuffleArea: Area? = null

    private val isRepeatEnabled = typedArray.getBoolean(
        R.styleable.MediaControllerView_repeat_enabled, false
    )
    private val isShuffleEnabled = typedArray.getBoolean(
        R.styleable.MediaControllerView_shuffle_enabled, false
    )

    init {
        setOnTouchListener(this)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            return when {
                Area.isTouched(playArea, event) -> {
                    onTouchPlayAndPause()
                    true
                }
                Area.isTouched(pauseArea, event) -> {
                    onTouchPlayAndPause()
                    true
                }
                Area.isTouched(previousArea, event) -> {
                    onTouchPrevious()
                    true
                }
                Area.isTouched(nextArea, event) -> {
                    onTouchNext()
                    true
                }
                Area.isTouched(repeatArea, event) -> {
                    onTouchRepeat()
                    true
                }
                Area.isTouched(shuffleArea, event) -> {
                    onTouchShuffle()
                    true
                }
                else -> false
            }
        }
        return true
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

    private fun onTouchRepeat() {
        repeat()
    }

    private fun onTouchShuffle() {
        shuffle()
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        invalidate()
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        invalidate()
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
        canvas.drawColor(ContextCompat.getColor(context, R.color.colorBlack))
        drawPrevious(canvas)
        drawPlayAndPause(canvas)
        drawNext(canvas)
        if (isRepeatEnabled) drawRepeat(canvas)
        if (isShuffleEnabled) drawShuffle(canvas)
    }

    private fun drawPrevious(canvas: Canvas) {
        val previousBitmap = paintBundle.previousBitmap
        if (previousArea == null) previousArea = Area(
            previousBitmap.width,
            previousBitmap.height,
            width * 0.35f,
            previousBitmap getCenterYFrom this
        )
        canvas.drawBitmap(previousBitmap, previousArea!!.x, previousArea!!.y, null)
    }

    private fun drawPlayAndPause(canvas: Canvas) {
        val playBitmap = paintBundle.playBitmap
        val pauseBitmap = paintBundle.pauseBitmap
        if (playArea == null) playArea = Area(
            playBitmap.width,
            playBitmap.height,
            width * 0.5f,
            playBitmap getCenterYFrom this
        )
        if (pauseArea == null) pauseArea = Area(
            pauseBitmap.width,
            pauseBitmap.height,
            width * 0.5f,
            pauseBitmap getCenterYFrom this
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
            width * 0.65f,
            nextBitmap getCenterYFrom this
        )
        canvas.drawBitmap(nextBitmap, nextArea!!.x, nextArea!!.y, null)
    }

    private fun drawRepeat(canvas: Canvas) {
        val repeatBitmap = when (_player?.repeatMode) {
            Player.REPEAT_MODE_OFF -> paintBundle.repeatOffBitmap
            Player.REPEAT_MODE_ONE -> paintBundle.repeatOneBitmap
            else -> paintBundle.repeatAllBitmap
        }
        if (repeatArea == null) repeatArea = Area(
            repeatBitmap.width,
            repeatBitmap.height,
            width * 0.85f,
            repeatBitmap getCenterYFrom this
        )
        canvas.drawBitmap(repeatBitmap, repeatArea!!.x, repeatArea!!.y, null)
    }

    private fun drawShuffle(canvas: Canvas) {
        val shuffleBitmap = when (_player?.shuffleModeEnabled) {
            true -> paintBundle.shuffleOnBitmap
            else -> paintBundle.shuffleOffBitmap
        }
        if (shuffleArea == null) shuffleArea = Area(
            shuffleBitmap.width,
            shuffleBitmap.height,
            width * 0.9f,
            shuffleBitmap getCenterYFrom this
        )
        canvas.drawBitmap(shuffleBitmap, shuffleArea!!.x, shuffleArea!!.y, null)
    }
}