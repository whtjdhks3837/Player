package baejang.dynamic_media_view.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.exoplayer2.Player

abstract class PlayerControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), View.OnTouchListener, Player.EventListener {
    abstract fun start(player: Player)
    abstract fun release()
}

interface VideoController {
    fun play()
    fun pause()
    fun next(): Boolean
    fun previous(): Boolean
    fun shuffle()
    fun repeat()
}

interface AudioController {
    fun setVolume(volume: Int)
    fun mute()
}

interface MediaController : VideoController, AudioController

interface TimeSeekController : Runnable

interface Hideable {

    companion object {
        private const val DEFAULT_HIDE_DELAY = 5000L
    }

    var onEnabled: ((Boolean) -> Unit)?
    fun hide()
    fun show()
    fun isHide(): Boolean
    fun isShow(): Boolean
    fun isAutoHide(): Boolean
    fun isHideEnabled(): Boolean
    fun hideDelay(): Long = DEFAULT_HIDE_DELAY
}