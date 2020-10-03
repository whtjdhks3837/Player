package baejang.dynamic_media_view.ui.view.video_controller

import android.content.Context
import android.util.AttributeSet
import baejang.dynamic_media_view.ui.view.Hideable
import baejang.dynamic_media_view.ui.view.PlayerControllerView
import baejang.dynamic_media_view.ui.view.VideoController

abstract class VideoControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : PlayerControllerView(context, attrs, defStyleAttr), VideoController, Hideable