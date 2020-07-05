package baejang.dynamic_media_view.util

import com.google.android.exoplayer2.Player

fun Player.isPlaying() =
    playbackState != Player.STATE_ENDED && playbackState != Player.STATE_IDLE && playWhenReady
