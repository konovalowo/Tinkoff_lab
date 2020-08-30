package com.example.tinkoff_lab.ui.randomfeed

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.tinkoff_lab.R
import com.example.tinkoff_lab.database.PostsDatabase
import com.example.tinkoff_lab.repository.PostsRepository
import kotlinx.coroutines.launch

class FeedRandomViewModel(private val app: Application) : ViewModel() {

    private val postsRepository = PostsRepository(PostsDatabase.getInstance(app))

    val post = postsRepository.post

    private val _onLoadFailedEvent = MutableLiveData<Boolean>()
    val onLoadFailedEvent: LiveData<Boolean>
        get() = _onLoadFailedEvent

    init {
        val startingFeedId = readStartingId()
        loadPost(if (startingFeedId != 0) startingFeedId else null)
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

    fun saveStartingId() {
        val feedId = post.value?.feedId ?: 0
        val sharedPreferences = app.getSharedPreferences("data", Context.MODE_PRIVATE)
        with (sharedPreferences.edit()) {
            putInt(app.getString(R.string.saved_start_feed_id_key), feedId)
            commit()
        }
    }

    private fun readStartingId(): Int? {
        val key = app.getString(R.string.saved_start_feed_id_key)
        return app.getSharedPreferences("data", Context.MODE_PRIVATE).getInt(key, 0)
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