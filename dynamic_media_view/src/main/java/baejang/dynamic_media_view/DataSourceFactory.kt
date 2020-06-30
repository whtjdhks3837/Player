package baejang.dynamic_media_view

import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.util.Util

fun DataSource.Factory.of(uri: Uri, extension: String): MediaSource {
    return when (Util.inferContentType(uri, extension)) {
        C.TYPE_DASH -> DashMediaSource.Factory(this).createMediaSource(uri)
        C.TYPE_SS -> SsMediaSource.Factory(this).createMediaSource(uri)
        C.TYPE_HLS -> HlsMediaSource.Factory(this).createMediaSource(uri)
        C.TYPE_OTHER -> ProgressiveMediaSource.Factory(this).createMediaSource(uri)
        else -> throw IllegalStateException(
            "Unsupported type: ${Util.inferContentType(uri, extension)}"
        )
    }
}
