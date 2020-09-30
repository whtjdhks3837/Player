package baejang.dynamic_media_view.ui.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.*
import androidx.core.content.ContextCompat
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.util.getBitmap
import baejang.dynamic_media_view.util.getCenterYFromParent
import baejang.dynamic_media_view.util.isPlaying
import baejang.dynamic_media_view.util.log
import com.google.android.exoplayer2.Player

class BasicPlayerControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), VideoControllerView, AudioControllerView {

    companion object {
        private const val DELAY_MILLIS = 1000L
        private const val MIN_HEIGHT_PX = 200
        private const val MIN_WIDTH_PX = 1000
    }

    private val playBitmap = context getBitmap R.drawable.ic_play_arrow_24dp
    private val pauseBitmap = context getBitmap R.drawable.ic_pause_24dp
    private val previousBitmap = context getBitmap R.drawable.ic_keyboard_arrow_left_24dp
    private val nextBitmap = context getBitmap R.drawable.ic_keyboard_arrow_right_24dp
    private val repeatOffBitmap = context getBitmap R.drawable.ic_repeat_off_24dp
    private val repeatOneBitmap = context getBitmap R.drawable.ic_repeat_one_24dp
    private val repeatAllBitmap = context getBitmap R.drawable.ic_repeat_all_24dp

    private var player: Player? = null

    override fun setPlayer(player: Player) {
        if (this.player != null) return
        this.player = player
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
    }

    private fun drawPrevious(canvas: Canvas) {
        val x = width * 0.2f
        val y = playBitmap getCenterYFromParent this
        canvas.drawBitmap(previousBitmap, x, y, null)
    }

    private fun drawPlayAndPause(canvas: Canvas) {
        val x = width * 0.4f
        val y = playBitmap getCenterYFromParent this
        val isPlaying = player?.isPlaying() ?: false
        canvas.drawBitmap(if (isPlaying) pauseBitmap else playBitmap, x, y, null)
    }

    override fun play() {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun next() {
        TODO("Not yet implemented")
    }

    override fun previous() {
        TODO("Not yet implemented")
    }

    override fun shuffle() {
        TODO("Not yet implemented")
    }

    override fun repeat() {
        TODO("Not yet implemented")
    }

    override fun setVolume(volume: Int) {
        TODO("Not yet implemented")
    }

    override fun mute() {
        TODO("Not yet implemented")
    }
}