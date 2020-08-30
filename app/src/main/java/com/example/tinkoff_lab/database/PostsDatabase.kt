package com.example.tinkoff_lab.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tinkoff_lab.api.Post

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class PostsDatabase : RoomDatabase() {
    abstract val postsDatabaseDao: PostsDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: PostsDatabase? = null

        fun getInstance(context: Context): PostsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostsDatabase::class.java,
                        "post_cache_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}