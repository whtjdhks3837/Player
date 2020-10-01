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
import baejang.dynamic_media_view.util.*
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.*

abstract class BasePlayerControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), VideoControllerView, AudioControllerView {

    protected val typedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.BasePlayerControllerView, 0, 0
    )
    protected var _player: Player? = null

    override fun start(player: Player) {
        if (_player != null) return
        log("start")
        _player = player
        _player?.addListener(this)
    }

    override fun release() {
        _player?.removeListener(this)
    }

    override fun play() {
        if (_player?.isPlaying() == true) return
        _player?.playWhenReady = true
    }

    override fun pause() {
        if (_player?.isPlaying() == false) return
        _player?.playWhenReady = false
    }

    override fun next(): Boolean {
        return if (_player?.hasNext() == false) false
        else {
            _player?.next()
            true
        }
    }

    override fun previous(): Boolean {
        return if (_player?.hasPrevious() == false) false
        else {
            _player?.previous()
            true
        }
    }

    override fun shuffle() {
        _player?.shuffleModeEnabled = true
    }

    override fun repeat() {
        _player?.repeatMode = when (_player?.repeatMode) {
            REPEAT_MODE_OFF -> REPEAT_MODE_ALL
            REPEAT_MODE_ALL -> REPEAT_MODE_ONE
            REPEAT_MODE_ONE -> REPEAT_MODE_OFF
            else -> return
        }
    }

    override fun setVolume(volume: Int) {}

    override fun mute() {}
}