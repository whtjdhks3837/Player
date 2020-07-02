package baejang.dynamic_media_view.data.source.provider

import android.content.Context
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.FileDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.*
import com.google.android.exoplayer2.util.Util
import java.io.File

class CacheDataSourceFactoryProvider
    : DataSourceFactoryProvider<CacheDataSourceFactoryProvider.Params, CacheDataSourceFactory> {

    companion object {
        private const val DOWNLOAD_CONTENT_DIRECTORY = "test_player_downloads"
    }

    override fun create(
        context: Context,
        params: DataSourceFactoryProvider.Params
    ): CacheDataSourceFactory {
        val databaseProvider = createDatabaseProvider(context)
        val cache = createCache(context, databaseProvider)
        val applicationName = context.applicationInfo.packageName
        val upstreamFactory = DefaultDataSourceFactory(
            context, DefaultHttpDataSourceFactory(Util.getUserAgent(context, applicationName))
        )
        return CacheDataSourceFactory(
            cache,
            upstreamFactory,
            FileDataSourceFactory(),
            null,
            CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
            null
        )
    }

    private fun createDatabaseProvider(context: Context): DatabaseProvider {
        return ExoDatabaseProvider(context)
    }

    private fun createCache(context: Context, databaseProvider: DatabaseProvider): Cache {
        val parent = context.getExternalFilesDir(null) ?: context.filesDir
        val file = File(parent,
            DOWNLOAD_CONTENT_DIRECTORY
        )
        return SimpleCache(file, NoOpCacheEvictor(), databaseProvider)
    }

    data class Params(
        val a: Int = -1
    ) : DataSourceFactoryProvider.Params
}