package com.crestinfosystems_jinay.a7minuteworkout

import android.content.Context
import androidx.room.Room
import com.crestinfosystems_jinay.a7minuteworkout.data.historydata.HistoryDatabase

object Graph {
    lateinit var database: HistoryDatabase
    val wishRepository by lazy {
        database.historyDAO()
    }

    fun provide(context: Context) {
        database =
            Room.databaseBuilder(context, HistoryDatabase::class.java, "workoutapp.db").build()
    }
}