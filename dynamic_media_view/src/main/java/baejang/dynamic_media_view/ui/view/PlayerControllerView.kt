package baejang.dynamic_media_view.ui.view

import android.view.View
import com.google.android.exoplayer2.Player

interface PlayerControllerView : View.OnTouchListener, Player.EventListener {
    fun start(player: Player)
    fun release()
    fun clickOnParent() {}
}

interface TimeSeekView : PlayerControllerView, Runnable

interface VideoControllerView : PlayerControllerView {
    fun play()
    fun pause()
    fun next(): Boolean
    fun previous(): Boolean
    fun shuffle()
    fun repeat()
}

interface AudioControllerView : PlayerControllerView {
    fun setVolume(volume: Int)
    fun mute()
}