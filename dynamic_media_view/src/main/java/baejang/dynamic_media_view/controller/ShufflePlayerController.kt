package baejang.dynamic_media_view.controller

class ShufflePlayerController(
    private val playerController: PlayerController
) : PlayerController by playerController, PlayerController.Shuffle {

    private val player = playerController.getPlayer()

    override fun setShuffle(isShuffle: Boolean) {
        player.shuffleModeEnabled = isShuffle
    }
}