package baejang.dynamic_media_view.controller

import com.google.android.exoplayer2.Player

class MultiplePlayerController(
    private val playerController: PlayerController
) : PlayerController by playerController, PlayerController.Multiple {

    private val player = playerController.getPlayer()

    override fun next(): Boolean {
        if (isRepeat()) return true
        if (!player.hasNext()) return false
        return player.next().let { true }
    }

    override fun previous(): Boolean {
        if (isRepeat()) return true
        if (!player.hasPrevious()) return false
        return player.previous().let { true }
    }

    private fun isRepeat(): Boolean {
        return if (player.repeatMode == Player.REPEAT_MODE_ONE) {
            player.seekTo(0)
            true
        } else {
            false
        }
    }
}