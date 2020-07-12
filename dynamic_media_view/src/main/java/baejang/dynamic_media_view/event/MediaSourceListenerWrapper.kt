package baejang.dynamic_media_view.event

import android.os.Handler
import baejang.dynamic_media_view.util.log
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceEventListener
import java.io.IOException

class MediaSourceListenerWrapper(
    private val mediaSource: MediaSource
) : MediaSourceListener, MediaSourceEventListener {

    private val handler = Handler()
    override var onLoadStarted: ((
        Int,
        MediaSource.MediaPeriodId?,
        MediaSourceEventListener.LoadEventInfo?,
        MediaSourceEventListener.MediaLoadData?
    ) -> Unit)? = null
    override var onMediaPeriodCreate: ((Int, MediaSource.MediaPeriodId?) -> Unit)? = null

    override fun start() {
        mediaSource.addEventListener(handler, this)
    }

    override fun pause() {
        mediaSource.removeEventListener(this)
    }

    override fun release() {
        mediaSource.removeEventListener(this)
    }

    override fun onLoadStarted(
        windowIndex: Int,
        mediaPeriodId: MediaSource.MediaPeriodId?,
        loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
        mediaLoadData: MediaSourceEventListener.MediaLoadData?
    ) {
        log("[onLoadStarted] windowIndex: $windowIndex")
        onLoadStarted?.invoke(windowIndex, mediaPeriodId, loadEventInfo, mediaLoadData)
    }

    override fun onDownstreamFormatChanged(
        windowIndex: Int,
        mediaPeriodId: MediaSource.MediaPeriodId?,
        mediaLoadData: MediaSourceEventListener.MediaLoadData?
    ) {
        log("[onDownstreamFormatChanged] windowIndex: $windowIndex")
    }

    override fun onUpstreamDiscarded(
        windowIndex: Int,
        mediaPeriodId: MediaSource.MediaPeriodId?,
        mediaLoadData: MediaSourceEventListener.MediaLoadData?
    ) {
        log("[onUpstreamDiscarded] windowIndex: $windowIndex")
    }

    override fun onMediaPeriodCreated(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?) {
        log("[onMediaPeriodCreated] windowIndex: $windowIndex")
        onMediaPeriodCreate?.invoke(windowIndex, mediaPeriodId)
    }

    override fun onLoadCanceled(
        windowIndex: Int,
        mediaPeriodId: MediaSource.MediaPeriodId?,
        loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
        mediaLoadData: MediaSourceEventListener.MediaLoadData?
    ) {
        log("[onLoadCanceled] windowIndex: $windowIndex")
    }

    override fun onMediaPeriodReleased(
        windowIndex: Int,
        mediaPeriodId: MediaSource.MediaPeriodId?
    ) {
        log("[onMediaPeriodReleased] windowIndex: $windowIndex")
    }

    override fun onReadingStarted(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?) {
        log("[onReadingStarted] windowIndex: $windowIndex")
    }

    override fun onLoadCompleted(
        windowIndex: Int,
        mediaPeriodId: MediaSource.MediaPeriodId?,
        loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
        mediaLoadData: MediaSourceEventListener.MediaLoadData?
    ) {
        log("[onLoadCompleted] windowIndex: $windowIndex")
    }

    override fun onLoadError(
        windowIndex: Int,
        mediaPeriodId: MediaSource.MediaPeriodId?,
        loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
        mediaLoadData: MediaSourceEventListener.MediaLoadData?,
        error: IOException?,
        wasCanceled: Boolean
    ) {
        log("[onLoadError] windowIndex: $windowIndex")
    }
}