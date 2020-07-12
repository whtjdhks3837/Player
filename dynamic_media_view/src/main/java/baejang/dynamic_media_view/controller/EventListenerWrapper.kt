package baejang.dynamic_media_view.controller

import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray

class EventListenerWrapper(private val player: Player) : Player.EventListener {

    var onPlayIdleCallback: ((Boolean, Int) -> Unit)? = null
    var onPlayBufferingCallback: ((Boolean, Int) -> Unit)? = null
    var onPlayReadyCallback: ((Boolean, Int) -> Unit)? = null
    var onPlayEndCallback: ((Boolean, Int) -> Unit)? = null
    var onRepeatModeCallback: ((Int) -> Unit)? = null
    var onShuffleEnabledCallback: ((Boolean) -> Unit)? = null
    var onErrorCallback: ((ExoPlaybackException?) -> Unit)? = null

    fun start() {
        player.addListener(this)
    }

    fun pause() {
        player.removeListener(this)
    }

    fun release() {
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

    override fun onTracksChanged(
        trackGroups: TrackGroupArray?,
        trackSelections: TrackSelectionArray?
    ) {
        super.onTracksChanged(trackGroups, trackSelections)
    }
}