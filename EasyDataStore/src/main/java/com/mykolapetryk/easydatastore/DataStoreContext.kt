package com.mykolapetryk.easydatastore

import android.content.Context
import androidx.startup.Initializer
import java.lang.ref.WeakReference

internal object EasyDataStore {
    private lateinit var context: WeakReference<Context>
    fun setContext(ctx: Context) { context = WeakReference(ctx.applicationContext) }
    fun getContext() = context.get()!!
}

internal class EasyDataStoreProvider: Initializer<Boolean> {
    override fun create(context: Context): Boolean {
        EasyDataStore.setContext(context)
        return false
    }
    override fun dependencies(): MutableList<Class<out Initializer<*>>> =
        mutableListOf()
}