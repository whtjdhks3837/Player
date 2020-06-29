package baejang.dynamic_media_view.controller

import android.os.Handler
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import baejang.dynamic_media_view.util.log

class MediaPlayerController(private val player: Player, private val handler: Handler) : Player.EventListener {

    companion object {
        const val PROGRESS_UPDATE_MILLI_SEC = 500L
    }

    var playIdleCallback: (() -> Unit)? = null
    var playBufferingCallback: (() -> Unit)? = null
    var playReadyCallback: (() -> Unit)? = null
    var playEndCallback: (() -> Unit)? = null
    var errorCallback: (() -> Unit)? = null

    var onPlayerStateChange: ((Player) -> Unit)? = null

    init {
        player.addListener(this)
    }

    private val progressUpdateThread = Runnable {
        onPlayerStateChange?.invoke(player)
        updateProgress()
    }

    private fun updateProgress() {
        handler.postDelayed(progressUpdateThread, PROGRESS_UPDATE_MILLI_SEC)
    }

    private fun release() {
        handler.removeCallbacks(progressUpdateThread)
    }

    fun isPlaying() = player.playbackState != Player.STATE_ENDED
            && player.playbackState != Player.STATE_IDLE
            && player.playWhenReady

    private fun seekTo(position: Int) {

    }

    fun start() {
        player.playWhenReady = true
    }

    fun pause() {
        player.playWhenReady = false
    }

    override fun onSeekProcessed() {
        log("onSeekProcessed")
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        log("onPlaybackParametersChanged ${playbackParameters?.speed}")
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        log("onTracksChanged ${trackGroups?.length} ${trackSelections?.length}")
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        error?.printStackTrace()
        log("onPlayerError ${error?.type}")
        errorCallback?.invoke()
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        log("onLoadingChanged $isLoading")
    }

    override fun onPositionDiscontinuity(reason: Int) {
        log("onPositionDiscontinuity $reason")
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        log("onRepeatModeChanged $repeatMode")
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        log("onShuffleModeEnabledChanged $shuffleModeEnabled")
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
        log("onTimelineChanged ${timeline?.periodCount} $reason")
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        log("onPlayerStateChanged $playWhenReady $playbackState")
        when (playbackState) {
            Player.STATE_IDLE -> {
                playIdleCallback?.invoke()
            }
            Player.STATE_BUFFERING -> {
                playBufferingCallback?.invoke()
            }
            Player.STATE_READY -> {
                if (playWhenReady) {
                    updateProgress()
                } else {
                    release()
                }
                playReadyCallback?.invoke()
            }
            Player.STATE_ENDED -> {
                release()
                playEndCallback?.invoke()
            }
        }
    }
}