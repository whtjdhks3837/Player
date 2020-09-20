package baejang.dynamic_media_view.event

import com.google.android.exoplayer2.SimpleExoPlayer

sealed class EventParams {
    data class Exo(val player: SimpleExoPlayer): EventParams()
}