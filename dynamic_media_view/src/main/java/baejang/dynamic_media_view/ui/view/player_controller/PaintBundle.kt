package baejang.dynamic_media_view.ui.view.player_controller

import android.content.Context
import android.util.AttributeSet
import baejang.dynamic_media_view.R
import baejang.dynamic_media_view.util.getBitmap
import baejang.dynamic_media_view.util.resize

class PaintBundle(private val context: Context, private val attrs: AttributeSet?) {

    companion object {
        private const val FIX_ICON_DP = 24
    }

    private val typedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.BasePlayerControllerView, 0, 0
    )

    val playBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasePlayerControllerView_play_src,
            R.drawable.ic_play_arrow_24dp
        )
    ).resize(context, FIX_ICON_DP)

    val pauseBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasePlayerControllerView_pause_src,
            R.drawable.ic_pause_24dp
        )
    ).resize(context, FIX_ICON_DP)

    val previousBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasePlayerControllerView_previous_src,
            R.drawable.ic_keyboard_arrow_left_24dp
        )
    ).resize(context, FIX_ICON_DP)

    val nextBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasePlayerControllerView_next_src,
            R.drawable.ic_keyboard_arrow_right_24dp
        )
    ).resize(context, FIX_ICON_DP)

    val repeatOffBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasePlayerControllerView_repeat_off_src,
            R.drawable.ic_repeat_off_24dp
        )
    ).resize(context, FIX_ICON_DP)

    val repeatOneBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasePlayerControllerView_repeat_one_src,
            R.drawable.ic_repeat_one_24dp
        )
    ).resize(context, FIX_ICON_DP)

    val repeatAllBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasePlayerControllerView_repeat_all_src,
            R.drawable.ic_repeat_all_24dp
        )
    ).resize(context, FIX_ICON_DP)

    val shuffleOnBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasePlayerControllerView_shuffle_on_src,
            R.drawable.ic_shuffle_on_24dp
        )
    ).resize(context, FIX_ICON_DP)

    val shuffleOffBitmap = context.getBitmap(
        typedArray.getResourceId(
            R.styleable.BasePlayerControllerView_shuffle_off_src,
            R.drawable.ic_shuffle_off_24dp
        )
    ).resize(context, FIX_ICON_DP)
}