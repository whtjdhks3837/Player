package baejang.dynamic_media_view.controller

class MultiplePlayerController(
    private val playerController: PlayerController
) : PlayerController by playerController, PlayerController.Multiple {

    private val player = playerController.getPlayer()

    override fun next(): Boolean {
        if (!player.hasNext()) return false
        player.next()
        return true
    }

    override fun previous(): Boolean {
        if (!player.hasPrevious()) return false
        player.previous()
        return true
    }
}