package baejang.dynamic_media_view.data.source.provider

import android.content.Context
import com.google.android.exoplayer2.upstream.DataSource

interface DataSourceFactoryProvider<T : DataSourceFactoryProvider.Params, R: DataSource.Factory> {

    interface Params

    fun create(context: Context, params: Params): R
}