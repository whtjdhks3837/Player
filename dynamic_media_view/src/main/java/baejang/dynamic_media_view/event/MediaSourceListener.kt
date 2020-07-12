package baejang.dynamic_media_view.event

import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceEventListener

interface MediaSourceListener {
    var onLoadStarted: ((
        Int,
        MediaSource.MediaPeriodId?,
        MediaSourceEventListener.LoadEventInfo?,
        MediaSourceEventListener.MediaLoadData?
    ) -> Unit)?
    var onMediaPeriodCreate: ((Int, MediaSource.MediaPeriodId?) -> Unit)?

    fun start()
    fun pause()
    fun release()
}