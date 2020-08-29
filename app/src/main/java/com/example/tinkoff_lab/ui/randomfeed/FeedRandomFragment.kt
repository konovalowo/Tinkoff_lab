package com.example.tinkoff_lab.ui.randomfeed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.tinkoff_lab.R
import com.example.tinkoff_lab.api.Post
import com.example.tinkoff_lab.databinding.FeedRandomFragmentBinding
import kotlinx.android.synthetic.main.feed_random_fragment.view.*

class FeedRandomFragment : Fragment() {

    companion object {
        fun newInstance() = FeedRandomFragment()
    }

    private lateinit var binding: FeedRandomFragmentBinding

    private lateinit var viewModel: FeedRandomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(FeedRandomViewModel::class.java)

        viewModel.post.observe(viewLifecycleOwner, Observer { newPost ->
            updatePost(newPost)
        })

        viewModel.onLoadFailedEvent.observe(viewLifecycleOwner, Observer { loadFailed ->
            if (loadFailed) onLoadFailed()
        })

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.feed_random_fragment,
            container,
            false
        )

        binding.fabNext.setOnClickListener {
            onNext()
            Log.i("FeedRandomFragment", "Fab pressed")
        }

        // for image binding adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    private fun onNext() {
        invalidatePost()
        viewModel.onNext()
    }

    private fun onPrevious() {
        viewModel.onPrevious()
    }

    private fun invalidatePost() {
        binding.postCard.post_name.visibility = View.GONE
    }

    private fun updatePost(newPost: Post) {
        binding.postCard.post_name.visibility = View.VISIBLE
        binding.postCard.post_name.text = newPost.description
    }

    fun onLoadFailed() {
        // nav to failure screen
        Log.i("FeedRandomFragment", "Navigated")
        this.view?.let {
            findNavController().navigate(R.id.action_feedRandomFragment_to_errorFragment)
        }
    }
}