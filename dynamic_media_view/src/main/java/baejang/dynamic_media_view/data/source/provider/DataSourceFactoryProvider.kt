package baejang.dynamic_media_view.data.source.provider

import android.content.Context
import com.google.android.exoplayer2.upstream.DataSource

interface DataSourceFactoryProvider<T : DataSourceFactoryProvider.Params, R : DataSource.Factory> {

    interface Params

    fun create(context: Context, params: Params): R

    companion object {
        fun of(context: Context, type: DataSourceType): DataSource.Factory {
            return when (type) {
                is DataSourceType.Cache -> CacheDataSourceFactoryProvider.create(
                    context.applicationContext, CacheDataSourceFactoryProvider.Params()
                )
            }
        }
    }
}