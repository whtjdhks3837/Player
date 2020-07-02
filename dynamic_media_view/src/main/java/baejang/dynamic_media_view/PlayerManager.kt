package baejang.dynamic_media_view

import android.content.Context
import baejang.dynamic_media_view.data.source.provider.CacheDataSourceFactoryProvider
import com.google.android.exoplayer2.upstream.DataSource

object PlayerManager {

    private lateinit var initializer: Initializer

    @JvmStatic
    fun init(context: Context) {
        if (::initializer.isInitialized) return
        initializer = Initializer(context)
    }

    @JvmStatic
    fun getDataSourceFactory() = initializer.dataSourceFactory

    private class Initializer(context: Context) {
        val dataSourceFactory: DataSource.Factory

        init {
            dataSourceFactory = CacheDataSourceFactoryProvider()
                .create(
                context,
                CacheDataSourceFactoryProvider.Params()
            )
        }
    }
}