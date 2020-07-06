package baejang.dynamic_media_view.controller

import com.google.android.exoplayer2.Player

interface PlayerController {
    fun start()
    fun pause()
    fun release()
    fun isPlaying(): Boolean
    fun getPlayer(): Player

    interface Multiple {
        fun next(): Boolean
        fun previous(): Boolean
    }

    interface Repeat {
        fun setRepeatMode(@Player.RepeatMode mode: Int)
    }

    interface Shuffle {
        fun setShuffle(isShuffle: Boolean)
    }
}