package baejang.dynamic_media_view.data.media.source

sealed class SourceType
sealed class Single : SourceType()
object Dash : Single()
object Ss : Single()
object Hls : Single()

sealed class Multiple : SourceType()
object Concatenating : Multiple()
object Looping : Multiple()
object Merge : Multiple()
object Clip : Multiple()
object Ads : Multiple()