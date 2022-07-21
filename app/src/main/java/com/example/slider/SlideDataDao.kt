package com.example.slider

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SlideDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSlideData(slideData: SlideData) : Long

    @Update
    suspend fun updateSlideData(slideData: SlideData)

    @Delete
    suspend fun deleteSlideData(slideData: SlideData)

    @Query("SELECT * FROM slider_data_table ORDER BY start_index")
    fun getData() : LiveData<List<SlideData>>

    @Query("SELECT COUNT(start_index) FROM slider_data_table")
    suspend fun getCount() : Int

    @Query("SELECT * FROM slider_data_table WHERE start_index = :myStart LIMIT 1")
    suspend fun getNext(myStart : Int) : SlideData

    @Query("SELECT * FROM slider_data_table WHERE end_index = :myStart LIMIT 1")
    suspend fun getPrev(myStart : Int) : SlideData

    @Query("SELECT COUNT(start_index) FROM slider_data_table WHERE start_index > :myStart")
    suspend fun getNextSize(myStart: Int) : Int

}