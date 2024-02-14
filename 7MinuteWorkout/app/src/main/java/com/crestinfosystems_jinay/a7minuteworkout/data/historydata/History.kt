package com.crestinfosystems_jinay.a7minuteworkout.data.historydata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history-data")
data class History(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo("currenttime", defaultValue = "(datetime('now'))")
    var timestamp: String = ""
)
