package com.route.newsc39.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.newsc39.api.model.Source
import com.route.newsc39.data.database.daos.SourceDao

@Database(entities = [Source::class], version = 1)
abstract class MyDataBase: RoomDatabase() {
    abstract fun getSourcesDao(): SourceDao

    companion object{
        private var myDataBase: MyDataBase? = null
        fun init(context: Context){
            if(myDataBase == null){
                myDataBase =
                    Room.databaseBuilder(context, MyDataBase::class.java, "My_database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

            }
        }
        fun getInstance(): MyDataBase{
            return myDataBase!!;
        }
    }
}