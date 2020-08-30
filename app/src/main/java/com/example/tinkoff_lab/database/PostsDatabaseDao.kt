package com.example.tinkoff_lab.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tinkoff_lab.api.Post

@Dao
interface PostsDatabaseDao {
    @Insert
    suspend fun insert(post: Post)

    @Query("SELECT * FROM posts WHERE feedId = :id")
    suspend fun get(id: Long): Post?
}