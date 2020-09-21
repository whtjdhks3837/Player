package baejang.dynamic_media_view.ui.view

import com.google.android.exoplayer2.Player

interface TimeSeekView : Runnable, Player.EventListener {
    fun setPlayer(player: Player)
    fun start()
    fun stop()
}