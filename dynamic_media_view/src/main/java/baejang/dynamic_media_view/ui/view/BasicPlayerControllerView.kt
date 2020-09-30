package baejang.dynamic_media_view.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.*
import androidx.core.content.ContextCompat
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.util.*
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.*

class BasicPlayerControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), VideoControllerView, AudioControllerView {

    companion object {
        private const val DELAY_MILLIS = 1000L
        private const val MIN_HEIGHT_PX = 200
        private const val MIN_WIDTH_PX = 1000
        private const val FIX_ICON_DP = 24
    }

    private val typedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.BasicPlayerControllerView, 0, 0
    )

    private val playBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasicPlayerControllerView_play_src,
            R.drawable.ic_play_arrow_24dp
        )
    ).resize(context, FIX_ICON_DP)

    private val pauseBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasicPlayerControllerView_pause_src,
            R.drawable.ic_pause_24dp
        )
    ).resize(context, FIX_ICON_DP)

    private val previousBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasicPlayerControllerView_previous_src,
            R.drawable.ic_keyboard_arrow_left_24dp
        )
    ).resize(context, FIX_ICON_DP)

    private val nextBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasicPlayerControllerView_next_src,
            R.drawable.ic_keyboard_arrow_right_24dp
        )
    ).resize(context, FIX_ICON_DP)

    private val repeatOffBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasicPlayerControllerView_repeat_off_src,
            R.drawable.ic_repeat_off_24dp
        )
    ).resize(context, FIX_ICON_DP)

    private val repeatOneBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasicPlayerControllerView_repeat_one_src,
            R.drawable.ic_repeat_one_24dp
        )
    ).resize(context, FIX_ICON_DP)

    private val repeatAllBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasicPlayerControllerView_repeat_all_src,
            R.drawable.ic_repeat_all_24dp
        )
    ).resize(context, FIX_ICON_DP)

    private val shuffleOnBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasicPlayerControllerView_shuffle_on_src,
            R.drawable.ic_shuffle_on_24dp
        )
    ).resize(context, FIX_ICON_DP)

    private val shuffleOffBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasicPlayerControllerView_shuffle_off_src,
            R.drawable.ic_shuffle_off_24dp
        )
    ).resize(context, FIX_ICON_DP)

    private var playArea: Area? = null
    private var pauseArea: Area? = null
    private var previousArea: Area? = null
    private var nextArea: Area? = null

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
        drawNext(canvas)
    }

    private fun drawPrevious(canvas: Canvas) {
        if (previousArea == null) previousArea = Area(
            previousBitmap.width,
            previousBitmap.height,
            width * 0.2f,
            previousBitmap getCenterYFromParent this
        )
        canvas.drawBitmap(previousBitmap, previousArea!!.x, previousArea!!.y, null)
    }

    private fun drawPlayAndPause(canvas: Canvas) {
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
        val isPlaying = player?.isPlaying() ?: false
        if (isPlaying) canvas.drawBitmap(pauseBitmap, pauseArea!!.x, pauseArea!!.y, null)
        else canvas.drawBitmap(playBitmap, playArea!!.x, playArea!!.y, null)
    }

    private fun drawNext(canvas: Canvas) {
        if (nextArea == null) nextArea = Area(
            nextBitmap.width,
            nextBitmap.height,
            width * 0.6f,
            nextBitmap getCenterYFromParent this
        )
        canvas.drawBitmap(nextBitmap, nextArea!!.x, nextArea!!.y, null)
    }

    override fun play() {
        if (player?.isPlaying() == true) return
        player?.playWhenReady = true
    }

    override fun pause() {
        if (player?.isPlaying() == false) return
        player?.playWhenReady = false
    }

    override fun next() {
        player?.next()
    }

    override fun previous() {
        player?.previous()
    }

    override fun shuffle() {
        player?.shuffleModeEnabled = true
    }

    override fun repeat() {
        player?.repeatMode = when (player?.repeatMode) {
            REPEAT_MODE_OFF -> REPEAT_MODE_ALL
            REPEAT_MODE_ALL -> REPEAT_MODE_ONE
            REPEAT_MODE_ONE -> REPEAT_MODE_OFF
            else -> return
        }
    }

    override fun setVolume(volume: Int) {
        TODO("Not yet implemented")
    }

    override fun mute() {
        TODO("Not yet implemented")
    }
}