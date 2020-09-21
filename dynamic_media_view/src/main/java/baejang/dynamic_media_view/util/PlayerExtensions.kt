package baejang.dynamic_media_view.util

import com.google.android.exoplayer2.Player

fun Player.isPlaying() =
    playbackState != Player.STATE_ENDED && playbackState != Player.STATE_IDLE && playWhenReady

fun Player.getPercent() = (currentPosition.toFloat() / duration.toFloat())

fun Player.getMaxTime() = timeToString(duration / 1000)

fun Player.getPlayTime() = timeToString(currentPosition / 1000)

private fun timeToString(second: Long): String {
    if (second <= 0) return "00:00"
    val min = (second / 60).toInt()
    val sec = (second % 60).toInt()
    val minStr = appendPreZeroIfOneDigit(min)
    val secStr = appendPreZeroIfOneDigit(sec)
    return "$minStr:$secStr"
}

private fun appendPreZeroIfOneDigit(num: Int) = if (num < 10) "0$num" else "$num"
