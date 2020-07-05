package baejang.dynamic_media_view.data.media.source

import androidx.core.net.toUri
import baejang.dynamic_media_view.data.media.Media
import baejang.dynamic_media_view.util.from
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource

class ConcatenatingMediaSourceProvider(
    private val mediaSet: Set<Media.Base>,
    private val dataSourceFactory: DataSource.Factory
) : MediaSourceProvider.Multiple<Media.Base> {

    private val mediaSource = ConcatenatingMediaSource()

    init {
        with(mediaSource) {
            clear()
            for (item in mediaSet) {
                addMediaSource(dataSourceFactory.from(item.url.toUri()))
            }
        }
    }

    override fun getItems(): Set<Media.Base> {
        return mediaSet
    }

    override fun getMediaSource(): MediaSource {
        return mediaSource
    }

    override fun hasNext(media: Media.Base): Boolean {
        val index = mediaSet.indexOf(media)
        if (index == -1) return false
        return index < mediaSet.size
    }

    override fun hasPrevious(media: Media.Base): Boolean {
        val index = mediaSet.indexOf(media)
        if (index == -1) return false
        return index > 0
    }

    override fun next(media: Media.Base): Media.Base? {
        TODO("Not yet implemented")
    }

    override fun previous(media: Media.Base): Media.Base? {
        TODO("Not yet implemented")
    }
}
