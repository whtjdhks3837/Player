package baejang.dynamic_media_view.data.media.source

import baejang.dynamic_media_view.data.media.Media
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource

interface MediaSourceProvider<T : Media> {

    fun getMediaSource(): MediaSource

    interface Multiple<T : Media> : MediaSourceProvider<T> {
        fun getItems(): Set<T>
        fun hasNext(media: T): Boolean
        fun hasPrevious(media: T): Boolean
        fun next(media: T): T?
        fun previous(media: T): T?
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
