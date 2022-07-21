package com.example.slider

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "slider_data_table")
data class SlideData(
    @PrimaryKey
    @ColumnInfo(name = "start_index")
    var intStart: Int,
    @ColumnInfo(name = "end_index")
    var intEnd : Int,
    @ColumnInfo(name = "cur_index")
    var curr: Int
)
