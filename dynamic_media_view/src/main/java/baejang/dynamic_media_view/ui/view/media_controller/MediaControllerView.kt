package baejang.dynamic_media_view.ui.view.media_controller

import android.content.Context
import android.util.AttributeSet
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.ui.view.Hideable
import baejang.dynamic_media_view.ui.view.MediaController
import baejang.dynamic_media_view.ui.view.PlayerControllerView
import baejang.dynamic_media_view.util.isPlaying
import baejang.dynamic_media_view.util.log
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.*

abstract class MediaControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : PlayerControllerView(context, attrs, defStyleAttr), MediaController, Hideable {

    protected val typedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.MediaControllerView, 0, 0
    )
    protected var _player: Player? = null
    override var onEnabled: ((Boolean) -> Unit)? = null

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
        _player?.shuffleModeEnabled = !(_player?.shuffleModeEnabled ?: false)
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

    override fun hide() {
        visibility = GONE
    }

    override fun show() {
        visibility = VISIBLE
    }

    override fun isHide(): Boolean {
        return !isShow()
    }

    override fun isShow(): Boolean {
        return visibility == VISIBLE
    }

    override fun isHideEnabled(): Boolean {
        return typedArray.getBoolean(R.styleable.MediaControllerView_hide_enable, false)
    }

    override fun isAutoHide(): Boolean {
        return typedArray.getBoolean(R.styleable.MediaControllerView_auto_hide, false)
    }
}