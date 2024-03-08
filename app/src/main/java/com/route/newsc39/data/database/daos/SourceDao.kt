package com.route.newsc39.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.route.newsc39.api.model.Source

@Dao
interface SourceDao {
    @Insert
    fun addSources(sources: List<Source>)

    @Query("delete from Source where category = :category")
    fun deleteDataById(category: String)

    @Query("select * from Source where category = :category")
    fun getSources(category: String): List<Source>
}