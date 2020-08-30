package com.example.tinkoff_lab.ui.randomfeed

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.tinkoff_lab.api.DevelopersLifeApi
import com.example.tinkoff_lab.api.Post
import com.example.tinkoff_lab.database.PostsDatabase
import com.example.tinkoff_lab.repository.PostsRepository
import kotlinx.coroutines.launch
import java.util.*

class FeedRandomViewModel(app: Application) : ViewModel() {

//    private val post = MutableLiveData<Post>()
//    val post: LiveData<Post>
//        get() = _post
    private val postsRepository = PostsRepository(PostsDatabase.getInstance(app))

    val post = postsRepository.post

    private val _onLoadFailedEvent = MutableLiveData<Boolean>()
    val onLoadFailedEvent: LiveData<Boolean>
        get() = _onLoadFailedEvent

//    private val _previousAvailable = MutableLiveData<Boolean>()
//    val previousAvailable: LiveData<Boolean>
//        get() = _previousAvailable

    init {
        Log.i("FeedRandom", "Whoop")
        loadPost(null)
    }

    fun onNext() {
        val feedId = post.value?.feedId
        loadPost(feedId?.plus(1))
    }

    fun onPrevious() {
        // use cached
        val feedId = post.value?.feedId
        if (feedId != null && feedId > 1)
            loadPost(feedId.minus(1))
    }

    private fun loadPost(feedId: Int?) {

        viewModelScope.launch {
            try {
                _onLoadFailedEvent.value = false

                Log.i("FeedRandom", "NextfeedId $feedId")
                postsRepository.getPost(feedId)// id

            } catch (e: Exception) {
                Log.w("FeedRandomViewModel", e.message.toString())
                onLoadFailed()
            }
        }
    }

    private fun onLoadFailed() {
        _onLoadFailedEvent.value = true
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FeedRandomViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FeedRandomViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}