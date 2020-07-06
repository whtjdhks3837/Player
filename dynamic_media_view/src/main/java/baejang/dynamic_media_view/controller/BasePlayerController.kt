package baejang.dynamic_media_view.controller

import com.google.android.exoplayer2.Player

class BasePlayerController(private val player: Player) : PlayerController {

    override fun start() {
        player.playWhenReady = true
    }

    override fun pause() {
        player.playWhenReady = false
    }

    override fun release() {
        player.release()
    }

    override fun isPlaying() =
        player.playbackState != Player.STATE_ENDED &&
                player.playbackState != Player.STATE_IDLE &&
                player.playWhenReady

    override fun getPlayer(): Player {
        return player
    }
}