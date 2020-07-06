package baejang.dynamic_media_view.data.source.provider

import android.content.Context
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory

interface DataSourceFactoryProvider<T : DataSourceFactoryProvider.Params, R : DataSource.Factory> {

    interface Params

    fun create(context: Context, params: Params): R

    companion object {
        private lateinit var cacheDataSourceFactory: CacheDataSourceFactory

        fun of(context: Context, type: DataSourceType): DataSource.Factory {
            return when (type) {
                DataSourceType.Cache -> {
                    if (::cacheDataSourceFactory.isInitialized) return cacheDataSourceFactory
                    CacheDataSourceFactoryProvider().create(
                        context.applicationContext, CacheDataSourceFactoryProvider.Params()
                    ).apply {
                        cacheDataSourceFactory = this
                    }
                }
            }
        }
    }
}