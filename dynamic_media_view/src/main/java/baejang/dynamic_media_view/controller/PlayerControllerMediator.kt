package baejang.dynamic_media_view.controller

import com.google.android.exoplayer2.Player

class PlayerControllerMediator(val player: Player) {
    private val base = BasePlayerController(player)
    private var multiple: MultiplePlayerController? = null
    private var repeat: RepeatPlayerController? = null
    private var shuffle: ShufflePlayerController? = null

    fun setAction(action: Action) {
        when (action) {
            Action.Multiple -> multiple ?: let {
                multiple = MultiplePlayerController(base)
            }
            Action.Repeat -> repeat ?: let {
                repeat = RepeatPlayerController(base)
            }
            Action.Shuffle -> shuffle ?: let {
                shuffle = ShufflePlayerController(base)
            }
        }
    }

    fun getMultiple() = multiple
    fun getRepeat() = repeat
    fun getShuffle() = shuffle

    enum class Action {
        Multiple, Repeat, Shuffle
    }
}