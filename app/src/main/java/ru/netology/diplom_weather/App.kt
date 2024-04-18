package ru.netology.diplom_weather

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.google.firebase.FirebaseApp
import okhttp3.OkHttpClient
import ru.netology.diplom_weather.di.AppModule
import ru.netology.diplom_weather.di.CoreModule

class App : Application(), ImageLoaderFactory {

    companion object {
        lateinit var appModule: AppModule
        lateinit var coreModule: CoreModule
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
        appModule = AppModule(applicationContext)
        coreModule = CoreModule(applicationContext)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this.applicationContext).newBuilder()
            .callFactory(OkHttpClient.Builder().build())
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this.applicationContext)
                    .maxSizePercent(0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)
                    .directory(this.applicationContext.cacheDir)
                    .build()
            }
            .respectCacheHeaders(false)
            .logger(DebugLogger())
            .build()
    }

}