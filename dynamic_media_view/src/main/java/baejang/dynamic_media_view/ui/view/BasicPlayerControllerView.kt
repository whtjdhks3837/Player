package baejang.dynamic_media_view.ui.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.util.getBitmap
import baejang.dynamic_media_view.util.getCenterYFromParent
import baejang.dynamic_media_view.util.isPlaying
import baejang.dynamic_media_view.util.log
import com.google.android.exoplayer2.Player

class BasicPlayerControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), PlayerControllerView {

    private val playBitmap = context getBitmap R.drawable.ic_play_arrow_24dp
    private val pauseBitmap = context getBitmap R.drawable.ic_pause_24dp
    private val previousBitmap = context getBitmap R.drawable.ic_keyboard_arrow_left_24dp
    private val nextBitmap = context getBitmap R.drawable.ic_keyboard_arrow_right_24dp
    private val repeatOffBitmap = context getBitmap R.drawable.ic_repeat_off_24dp
    private val repeatOneBitmap = context getBitmap R.drawable.ic_repeat_one_24dp
    private val repeatAllBitmap = context getBitmap R.drawable.ic_repeat_all_24dp

    private var player: Player? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = when (val w = MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> if (w <= 600) 600 else MeasureSpec.getMode(widthMeasureSpec)
            MeasureSpec.AT_MOST -> 600
            else -> MeasureSpec.getSize(widthMeasureSpec)
        }
        val height = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> 200
            else -> MeasureSpec.getSize(heightMeasureSpec)
        }
        setMeasuredDimension(width, height)
        log("Custom view density ${resources.displayMetrics.density}")
        log("onMeasure : $width , $height")
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    override fun setPlayer(player: Player) {
        this.player = player
    }
}