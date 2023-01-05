package com.jalalkun.session.app

import android.app.Application
import com.jalalkun.session.Session

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Session.init(applicationContext, "${BuildConfig.APPLICATION_ID}${BuildConfig.BUILD_TYPE}")
    }
}
