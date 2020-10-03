package baejang.dynamic_media_view.ui.view.audio_controller

import android.content.Context
import android.util.AttributeSet
import baejang.dynamic_media_view.ui.view.AudioController
import baejang.dynamic_media_view.ui.view.Hideable
import baejang.dynamic_media_view.ui.view.PlayerControllerView

abstract class AudioControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : PlayerControllerView(context, attrs, defStyleAttr), AudioController, Hideable