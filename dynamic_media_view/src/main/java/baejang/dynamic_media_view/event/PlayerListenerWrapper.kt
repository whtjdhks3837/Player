package baejang.dynamic_media_view.event

import baejang.dynamic_media_view.util.log
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray

class PlayerListenerWrapper(
    private val player: Player
) : PlayerListener, Player.EventListener {

    override var onPlayIdleCallback: ((Boolean, Int) -> Unit)? = null
    override var onPlayBufferingCallback: ((Boolean, Int) -> Unit)? = null
    override var onPlayReadyCallback: ((Boolean, Int) -> Unit)? = null
    override var onPlayEndCallback: ((Boolean, Int) -> Unit)? = null
    override var onRepeatModeCallback: ((Int) -> Unit)? = null
    override var onShuffleEnabledCallback: ((Boolean) -> Unit)? = null
    override var onErrorCallback: ((ExoPlaybackException?) -> Unit)? = null

    override fun start() {
        player.addListener(this)
    }

    override fun pause() {
        player.removeListener(this)
    }

    override fun release() {
        player.removeListener(this)
        onPlayIdleCallback = null
        onPlayBufferingCallback = null
        onPlayReadyCallback = null
        onPlayEndCallback = null
        onRepeatModeCallback = null
        onShuffleEnabledCallback = null
        onErrorCallback = null
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        log("[onPlayerStateChanged]")
        when (playbackState) {
            Player.STATE_IDLE -> onPlayIdleCallback?.invoke(playWhenReady, playbackState)
            Player.STATE_BUFFERING -> onPlayBufferingCallback?.invoke(playWhenReady, playbackState)
            Player.STATE_READY -> onPlayReadyCallback?.invoke(playWhenReady, playbackState)
            Player.STATE_ENDED -> onPlayEndCallback?.invoke(playWhenReady, playbackState)
        }
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        super.onPlayerError(error)
        player.release()
        onErrorCallback?.invoke(error)
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        super.onRepeatModeChanged(repeatMode)
        onRepeatModeCallback?.invoke(repeatMode)
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        super.onShuffleModeEnabledChanged(shuffleModeEnabled)
        onShuffleEnabledCallback?.invoke(shuffleModeEnabled)
    }
}