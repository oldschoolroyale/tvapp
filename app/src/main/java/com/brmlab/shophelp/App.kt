package com.brmlab.shophelp

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import dagger.hilt.android.HiltAndroidApp
import java.io.File

@HiltAndroidApp
class App:  MultiDexApplication() {

    companion object{
        var simpleCache:SimpleCache? = null

        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "f82ad63f8dc601e70e9c8826004ee9ec"
        const val GOOGLE_API = "AIzaSyAU579_y1NY065MI8TU8t_qL6Nau4MJX4U"
    }

    override fun onCreate() {
        super.onCreate()
        val evictor = LeastRecentlyUsedCacheEvictor(1000 * 1024 * 1024)
        var cacheDir: File = File(this.cacheDir, "movieApp") //Your cache dir


        simpleCache   = SimpleCache(cacheDir, evictor, ExoDatabaseProvider(this))
    }

}