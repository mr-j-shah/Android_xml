package com.crestinfosystems_jinay.a7minuteworkout.data.historydata

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [History::class], version = 1, exportSchema = true)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDAO(): HistoryDAO
}