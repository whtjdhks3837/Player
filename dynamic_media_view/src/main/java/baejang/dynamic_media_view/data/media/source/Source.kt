package baejang.dynamic_media_view.data.media.source

import baejang.dynamic_media_view.data.media.Media

interface Source<Type : SourceType> {
    interface Provider<T : Media>
}

interface SingleSource<T : Single> :
    Source<T> {

    interface Provider<T : Media> :
        Source.Provider<T> {
        fun setItem(media: T)
        fun getItem(): T
    }
}

interface MultipleSource<T : Multiple> :
    Source<T> {

    interface Provider<T : Media> :
        Source.Provider<T> {
        fun setItems(mediaSet: Set<T>)
        fun getItems(): Set<T>
        fun hasNext(media: T): Boolean
        fun hasPrevious(media: T): Boolean
        fun next(media: T): T?
        fun previous(media: T): T?
    }
}