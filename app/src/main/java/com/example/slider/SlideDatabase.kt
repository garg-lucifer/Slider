package com.example.slider

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SlideData::class], version = 1)
abstract class SlideDatabase : RoomDatabase(){
    abstract val slideDataDao : SlideDataDao

    companion object{
        @Volatile
        private var INSTANCE : SlideDatabase?= null
         fun getInstance(context : Context) : SlideDatabase{
             synchronized(this){
                 var instance = INSTANCE
                 if(instance == null)
                     instance = Room.databaseBuilder(
                                context.applicationContext,
                                SlideDatabase::class.java,
                                "slider_data_table").build()
                 return instance
             }
         }
    }

}