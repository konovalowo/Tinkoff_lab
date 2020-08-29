package com.example.tinkoff_lab.ui.randomfeed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoff_lab.api.DevelopersLifeApi
import com.example.tinkoff_lab.api.Post
import kotlinx.coroutines.launch
import java.util.*

class FeedRandomViewModel : ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = _post

    private val _onLoadFailedEvent = MutableLiveData<Boolean>()
    val onLoadFailedEvent: LiveData<Boolean>
        get() = _onLoadFailedEvent

    private val cacheStack = Stack<Post>()

    init {
        onNext()
    }

    fun onNext() {
        loadPost()
    }

    fun onPrevious() {
        // use cached
        TODO()
    }

    private fun loadPost() {
        viewModelScope.launch {
            try {
                _onLoadFailedEvent.value = false

                val post = DevelopersLifeApi.retrofitService.getRandomPost()
                _post.value = post
            } catch (e: Exception) {
                Log.w("FeedRandomViewModel", e.message.toString())
                onLoadFailed()
            }
        }
    }

    private fun onLoadFailed() {
        _onLoadFailedEvent.value = true
    }
}