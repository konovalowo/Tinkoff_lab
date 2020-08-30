package com.example.tinkoff_lab.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.tinkoff_lab.api.DevelopersLifeApi
import com.example.tinkoff_lab.api.Post
import com.example.tinkoff_lab.database.PostsDatabase
import com.example.tinkoff_lab.database.PostsDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsRepository(private val database: PostsDatabase) {

    val post: MutableLiveData<Post> = MutableLiveData()


    suspend fun getPost(feedId: Int?) {
        withContext(Dispatchers.IO) {

            val newPostDb = if (feedId == null) database.postsDatabaseDao.getLast()
                else database.postsDatabaseDao.get(feedId.toLong())

            if (newPostDb == null) {
                val newPost = DevelopersLifeApi.retrofitService.getRandomPost()

                val id = database.postsDatabaseDao.insert(newPost)
                newPost.feedId = id.toInt()
                Log.i("PostsRepository", "Inserted post id $id")

                post.postValue(newPost)
            } else {
                post.postValue(newPostDb)
            }
        }
    }
}