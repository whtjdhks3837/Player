package baejang.dynamic_media_view.ui.view

import com.google.android.exoplayer2.Player

interface PlayerControllerView {
    fun setPlayer(player: Player)
}

interface TimeSeekView : PlayerControllerView, Runnable, Player.EventListener {
    fun start()
    fun stop()
}

interface VideoControllerView : PlayerControllerView {
    fun play()
    fun pause()
    fun next()
    fun previous()
    fun shuffle()
    fun repeat()
}

interface AudioControllerView : PlayerControllerView {
    fun setVolume(volume: Int)
    fun mute()
}