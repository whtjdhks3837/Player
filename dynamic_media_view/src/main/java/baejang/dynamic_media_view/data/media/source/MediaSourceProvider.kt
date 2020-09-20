package baejang.dynamic_media_view.data.media.source

import baejang.dynamic_media_view.data.media.Media
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource

interface MediaSourceProvider<out T : Media> {

    fun getMediaSource(): MediaSource

    interface Multiple<T : Media> : MediaSourceProvider<T> {
        fun getItems(): Set<T>
    }

    interface Single<T : Media> : MediaSourceProvider<T> {
        fun getItem(): T
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun of(
            mediaSet: Set<Media>,
            dataSourceFactory: DataSource.Factory,
            type: MediaSourceType
        ): MediaSourceProvider<*> {
            return when (type) {
                is MediaSourceType.Multiple.Concatenating -> ConcatenatingMediaSourceProvider(
                    mediaSet as Set<Media.Base>, dataSourceFactory
                )
                else -> throw NotImplementedError()
            }
        }
    }
}
