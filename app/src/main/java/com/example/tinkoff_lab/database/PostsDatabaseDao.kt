package com.example.tinkoff_lab.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tinkoff_lab.api.Post

@Dao
interface PostsDatabaseDao {
    @Insert
    suspend fun insert(post: Post): Long

    @Query("SELECT * FROM posts WHERE feedId = :id")
    suspend fun get(id: Long): Post?

    @Query("SELECT * FROM posts ORDER BY feedId DESC LIMIT 1")
    suspend fun getLast(): Post?
}