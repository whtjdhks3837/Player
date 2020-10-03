package baejang.dynamic_media_view

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import baejang.dynamic_media_view.data.media.Media
import baejang.dynamic_media_view.data.media.source.ConcatenatingMediaSourceProvider
import baejang.dynamic_media_view.data.media.source.MediaSourceProvider
import baejang.dynamic_media_view.data.media.source.MediaSourceType
import baejang.dynamic_media_view.data.source.provider.DataSourceFactoryProvider
import baejang.dynamic_media_view.data.source.provider.DataSourceType
import baejang.dynamic_media_view.ui.PlayerActivity
import baejang.dynamic_media_view.ui.view.ControllerType

object PlayerManager {
    private lateinit var mediaSourceProvider: MediaSourceProvider<*>

    const val CONTROLLER_TYPE = "controller_type"

    @JvmStatic
    fun <T : Media> start(context: Context, params: Params<T>) {
        val dataSourceFactory =
            DataSourceFactoryProvider.of(context, params.dataSourceType)
        mediaSourceProvider = MediaSourceProvider.of(
            params.mediaSet, dataSourceFactory, params.mediaSourceType
        )
        val intent = Intent(context, PlayerActivity::class.java).apply {
            putExtra(CONTROLLER_TYPE, params.controllerType.value)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) addFlags(FLAG_ACTIVITY_NEW_TASK)
        }
        when (mediaSourceProvider) {
            is ConcatenatingMediaSourceProvider -> context.startActivity(intent)
        }
    }

    @JvmStatic
    fun getMediaSourceProvider(): MediaSourceProvider<*> = mediaSourceProvider

    class Params<T : Media> private constructor(builder: Builder<T>) {

        val mediaSet = builder.getMediaSet()
        val mediaSourceType = builder.getMediaSourceType()
        val dataSourceType = builder.getDataSourceType()
        val controllerType = builder.getControllerType()

        class Builder<T : Media> {
            private val mediaSet = mutableSetOf<T>()
            private var mediaSourceType: MediaSourceType = MediaSourceType.Multiple.Concatenating
            private var dataSourceType: DataSourceType = DataSourceType.Cache
            private var controllerType: ControllerType = ControllerType.Default

            fun setMediaSet(mediaSet: Set<T>): Builder<T> {
                this.mediaSet.clear()
                this.mediaSet.addAll(mediaSet)
                return this
            }

            fun getMediaSet(): Set<T> = mediaSet

            fun setMediaSourceType(type: MediaSourceType): Builder<T> {
                this.mediaSourceType = type
                return this
            }

            fun getMediaSourceType() = mediaSourceType

            fun setDataSourceType(type: DataSourceType): Builder<T> {
                this.dataSourceType = type
                return this
            }

            fun getDataSourceType() = dataSourceType

            fun setControllerType(type: ControllerType): Builder<T> {
                this.controllerType = type
                return this
            }

            fun getControllerType() = controllerType

            fun build() = Params(this)
        }
    }
}