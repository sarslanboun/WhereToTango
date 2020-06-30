package com.serdararslan.wheretotango

import android.app.Application
import com.parse.Parse

class StarterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)

        Parse.initialize(Parse.Configuration.Builder(this)
            .applicationId("kW2sT8Yos1JRHWHQqTRuBftUbew3vkJ7JcFSE4bL")
            .clientKey("5vIXajI7YO8U6vUolYmfNWzStvPsr6nfHKCdyC8c")
            .server("https://parseapi.back4app.com/")
            .build())
    }
}