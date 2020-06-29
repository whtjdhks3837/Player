package baejang.mediaview

import android.app.Application
import baejang.dynamic_media_view.ExoPlayerUtil

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ExoPlayerUtil.init(this)
    }
}