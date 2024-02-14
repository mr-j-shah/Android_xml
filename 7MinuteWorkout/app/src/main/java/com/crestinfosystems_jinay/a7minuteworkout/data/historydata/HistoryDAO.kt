package com.crestinfosystems_jinay.a7minuteworkout.data.historydata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
abstract class HistoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertHistory(history: History)

    @Delete()
    abstract suspend fun deleteExpanse(history: History)

    @Update
    abstract suspend fun updateAExpanse(history: History)

    @Query("select * from `history-data`")
    abstract fun getAllHistory(): Flow<List<History>>

}