package baejang.dynamic_media_view.data.media.source

sealed class MediaSourceType {
    sealed class Single : MediaSourceType() {
        object Dash : Single()
        object Ss : Single()
        object Hls : Single()
    }

    sealed class Multiple : MediaSourceType() {
        object Concatenating : Multiple()
        object Looping : Multiple()
        object Merge : Multiple()
        object Clip : Multiple()
        object Ads : Multiple()
    }
}