package baejang.dynamic_media_view.event

import baejang.dynamic_media_view.util.log
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.analytics.AnalyticsListener

class ExoEventListener(private val player: SimpleExoPlayer) : PlayerListener, AnalyticsListener {

    override var onPlayIdleCallback: ((Boolean, Int) -> Unit)? = null
    override var onPlayBufferingCallback: ((Boolean, Int) -> Unit)? = null
    override var onPlayReadyCallback: ((Boolean, Int) -> Unit)? = null
    override var onPlayEndCallback: ((Boolean, Int) -> Unit)? = null
    override var onSeekStartedCallback: ((Long) -> Unit)? = null
    override var onSeekProcessedCallback: ((Long) -> Unit)? = null
    override var onRepeatModeCallback: ((Int) -> Unit)? = null
    override var onShuffleEnabledCallback: ((Boolean) -> Unit)? = null
    override var onErrorCallback: ((ExoPlaybackException?) -> Unit)? = null

    override fun start() {
        player.addAnalyticsListener(this)
    }

    override fun pause() {
        player.removeAnalyticsListener(this)
    }

    override fun release() {
        player.removeAnalyticsListener(this)
        onPlayIdleCallback = null
        onPlayBufferingCallback = null
        onPlayReadyCallback = null
        onPlayEndCallback = null
        onRepeatModeCallback = null
        onShuffleEnabledCallback = null
        onErrorCallback = null
    }

    override fun onPlayerStateChanged(
        eventTime: AnalyticsListener.EventTime?,
        playWhenReady: Boolean,
        playbackState: Int
    ) {
        super.onPlayerStateChanged(eventTime, playWhenReady, playbackState)
        log("[onPlayerStateChanged] $playWhenReady , $playbackState")
        when (playbackState) {
            Player.STATE_IDLE -> onPlayIdleCallback?.invoke(playWhenReady, playbackState)
            Player.STATE_BUFFERING -> onPlayBufferingCallback?.invoke(playWhenReady, playbackState)
            Player.STATE_READY -> onPlayReadyCallback?.invoke(playWhenReady, playbackState)
            Player.STATE_ENDED -> onPlayEndCallback?.invoke(playWhenReady, playbackState)
        }
    }

    override fun onPlayerError(
        eventTime: AnalyticsListener.EventTime?,
        error: ExoPlaybackException?
    ) {
        super.onPlayerError(eventTime, error)
        log("[onPlayerError]")
        player.release()
        onErrorCallback?.invoke(error)
    }

    override fun onRepeatModeChanged(eventTime: AnalyticsListener.EventTime?, repeatMode: Int) {
        super.onRepeatModeChanged(eventTime, repeatMode)
        log("[onRepeatModeChanged] $repeatMode")
        onRepeatModeCallback?.invoke(repeatMode)
    }

    override fun onShuffleModeChanged(
        eventTime: AnalyticsListener.EventTime?,
        shuffleModeEnabled: Boolean
    ) {
        super.onShuffleModeChanged(eventTime, shuffleModeEnabled)
        log("[onShuffleModeChanged] $shuffleModeEnabled")
        onShuffleEnabledCallback?.invoke(shuffleModeEnabled)
    }

    override fun onSeekStarted(eventTime: AnalyticsListener.EventTime?) {
        super.onSeekStarted(eventTime)
        log("[onSeekStarted] $eventTime")
        onSeekStartedCallback?.invoke(eventTime?.currentPlaybackPositionMs ?: 0)

    }

    override fun onSeekProcessed(eventTime: AnalyticsListener.EventTime?) {
        super.onSeekProcessed(eventTime)
        log("[onSeekProcessed] $eventTime")
        onSeekProcessedCallback?.invoke(eventTime?.currentPlaybackPositionMs ?: 0)
    }

    override fun onPositionDiscontinuity(eventTime: AnalyticsListener.EventTime?, reason: Int) {
        super.onPositionDiscontinuity(eventTime, reason)
        log("[onPositionDiscontinuity] $eventTime")
    }


}