package baejang.dynamic_media_view

import android.content.Context
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.offline.*
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.FileDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.*
import com.google.android.exoplayer2.util.Util
import java.io.File
import java.io.IOException
import kotlin.IllegalStateException

object ExoPlayerUtil {
    private const val DOWNLOAD_CONTENT_DIRECTORY = "downloads"

    private lateinit var userAgent: String
    private lateinit var downloadDirectory: File
    private lateinit var downloadCache: SimpleCache
    private lateinit var databaseProvider: ExoDatabaseProvider

    lateinit var buildDataSourceFactory: CacheDataSourceFactory
    lateinit var renderersFactory: RenderersFactory

    private var isInit = false

    fun init(context: Context) {
        if (!isInit) {
            userAgent = Util.getUserAgent(context, "")
            databaseProvider = ExoDatabaseProvider(context)
            downloadDirectory = context.getExternalFilesDir(null) ?: context.filesDir
            downloadCache = SimpleCache(
                File(downloadDirectory, DOWNLOAD_CONTENT_DIRECTORY), NoOpCacheEvictor(), databaseProvider
            )
            buildDataSourceFactory = buildReadOnlyCacheDataSource(
                DefaultDataSourceFactory(context, buildHttpDataSourceFactory()), downloadCache
            )
            renderersFactory = DefaultRenderersFactory(context).apply {
                setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)
            }
            isInit = true
            return
        }
        throw IllegalStateException()
    }

    private fun buildHttpDataSourceFactory() = DefaultHttpDataSourceFactory(userAgent)

    private fun buildReadOnlyCacheDataSource(upstreamFactory: DataSource.Factory, cache: Cache) =
        CacheDataSourceFactory(
            cache,
            upstreamFactory,
            FileDataSourceFactory(),
            null,
            CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
            null
        )
}