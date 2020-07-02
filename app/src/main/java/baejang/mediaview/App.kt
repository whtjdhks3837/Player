package baejang.mediaview

import android.app.Application
import baejang.dynamic_media_view.PlayerManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        PlayerManager.init(this)
    }
}