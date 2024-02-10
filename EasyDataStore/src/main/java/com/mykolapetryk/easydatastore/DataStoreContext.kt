package com.mykolapetryk.easydatastore

import android.app.Application
import android.content.Context

class EasyDataStore: Application() {

    init { instance = this }

    companion object {
        private var instance: EasyDataStore? = null

        fun context() : Context {
            return instance!!.applicationContext
        }
    }
}