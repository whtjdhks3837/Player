package baejang.dynamic_media_view.data.source

import androidx.core.net.toUri
import baejang.dynamic_media_view.data.BaseMedia
import baejang.dynamic_media_view.of
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.upstream.DataSource

class ConcatenatingMediaSourceImpl(
    private val provider: Provider,
    private val dataSourceFactory: DataSource.Factory
) : MultipleSource<Concatenating> {

    val mediaSource = ConcatenatingMediaSource()

    init {
        with(mediaSource) {
            clear()
            for (item in provider.getItems()) {
                addMediaSource(dataSourceFactory.of(item.url.toUri(), item.extension))
            }
        }
    }

    class Provider : MultipleSource.Provider<BaseMedia> {
        private val mediaSet = mutableSetOf<BaseMedia>()

        override fun setItems(mediaSet: Set<BaseMedia>) {
            with(this.mediaSet) {
                clear()
                addAll(mediaSet)
            }
        }

        override fun getItems(): Set<BaseMedia> {
            return mediaSet
        }

        override fun hasNext(media: BaseMedia): Boolean {
            val index = mediaSet.indexOf(media)
            if (index == -1) return false
            return index < mediaSet.size
        }

        override fun hasPrevious(media: BaseMedia): Boolean {
            val index = mediaSet.indexOf(media)
            if (index == -1) return false
            return index > 0
        }

        override fun next(media: BaseMedia): BaseMedia? {
            TODO("Not yet implemented")
        }

        override fun previous(media: BaseMedia): BaseMedia? {
            TODO("Not yet implemented")
        }
    }
}
