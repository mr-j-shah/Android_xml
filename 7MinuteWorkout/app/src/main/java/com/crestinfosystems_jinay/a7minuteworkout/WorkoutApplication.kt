package com.crestinfosystems_jinay.a7minuteworkout

import android.app.Application

class WorkoutApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}