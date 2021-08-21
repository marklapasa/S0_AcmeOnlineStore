package com.example.acmeonlinestore

import android.app.Application
import android.content.res.Resources

class AcmeOnlineStoreApp : Application() {
    companion object {
        private var resources: Resources? = null
        fun getResources(): Resources? {
            return resources
        }
    }

    override fun onCreate() {
        super.onCreate()
        AcmeOnlineStoreApp.resources = applicationContext.resources
    }
}