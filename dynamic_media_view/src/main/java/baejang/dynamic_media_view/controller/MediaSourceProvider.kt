package baejang.dynamic_media_view.controller

import android.net.Uri
import androidx.core.net.toUri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.util.Util
import baejang.dynamic_media_view.ExoPlayerUtil
import baejang.dynamic_media_view.data.Media
import baejang.dynamic_media_view.data.PlayList

class MediaSourceProvider(private val playList: PlayList) {

    private val dataSourceFactory = ExoPlayerUtil.buildDataSourceFactory
    val mediaSource = ConcatenatingMediaSource()

    private var position = 0

    fun setMediaSource(position: Int) {
        mediaSource.apply {
            this@MediaSourceProvider.position = position
            if (playList.playList.isEmpty())
                return
            clear()
            val mediaBundle = playList.playList[position]
            mediaBundle.forEach {
                addMediaSource(MediaDataSourceFactory.create(dataSourceFactory, it.url.toUri(), it.extension))
            }
        }
    }

    fun hasNext() = playList.playList.size - 1 > position

    fun hasPrevious() = position - 1 >= 0

    fun next() = setMediaSource(++position)

    fun previous() = setMediaSource(--position)

    fun getCurrentMediaBundle() = getMediaBundle(position)

    fun getMediaBundle(position: Int): List<Media>? {
        if (playList.playList.isEmpty()) {
            return null
        }
        return if (position in 0..playList.playList.size) {
            playList.playList[position]
        } else {
            null
        }
    }
}

object MediaDataSourceFactory {
    fun create(dataSourceFactory: DataSource.Factory, uri: Uri, extension: String): MediaSource =
        when (Util.inferContentType(uri, extension)) {
            C.TYPE_DASH -> {
                DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            }
            C.TYPE_SS -> {
                SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            }
            C.TYPE_HLS -> {
                HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            }
            C.TYPE_OTHER -> {
                ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            }
            else -> {
                throw IllegalStateException("Unsupported type: ${Util.inferContentType(uri, extension)}")
            }
        }
}