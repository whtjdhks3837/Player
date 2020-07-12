package baejang.dynamic_media_view.event

import com.google.android.exoplayer2.ExoPlaybackException

interface PlayerListener {
    var onPlayIdleCallback: ((Boolean, Int) -> Unit)?
    var onPlayBufferingCallback: ((Boolean, Int) -> Unit)?
    var onPlayReadyCallback: ((Boolean, Int) -> Unit)?
    var onPlayEndCallback: ((Boolean, Int) -> Unit)?
    var onRepeatModeCallback: ((Int) -> Unit)?
    var onShuffleEnabledCallback: ((Boolean) -> Unit)?
    var onErrorCallback: ((ExoPlaybackException?) -> Unit)?

    fun start()
    fun pause()
    fun release()
}