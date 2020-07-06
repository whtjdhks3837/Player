package baejang.dynamic_media_view.controller

class RepeatPlayerController(
    private val playerController: PlayerController
) : PlayerController by playerController, PlayerController.Repeat {

    private val player = playerController.getPlayer()

    override fun setRepeatMode(mode: Int) {
        player.repeatMode = mode
    }
}