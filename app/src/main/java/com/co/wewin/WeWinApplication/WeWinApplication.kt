package com.co.wewin.WeWinApplication

import android.app.Application

/**
 * @author Vikas Sharma
 */

class WeWinApplication:Application() {
    companion object{
        private var instance = WeWinApplication()
        fun getInstance() : WeWinApplication{
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}