package com.example.tinkoff_lab.ui.randomfeed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tinkoff_lab.R
import com.example.tinkoff_lab.api.Post
import com.example.tinkoff_lab.databinding.FragmentFeedRandomBinding
import kotlinx.android.synthetic.main.fragment_feed_random.view.*

class FeedRandomFragment : Fragment() {

    companion object {
        fun newInstance() = FeedRandomFragment()
    }

    private lateinit var binding: FragmentFeedRandomBinding

    private lateinit var viewModel: FeedRandomViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val viewModelFactory = FeedRandomViewModel.Factory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(FeedRandomViewModel::class.java)

        viewModel.post.observe(viewLifecycleOwner, Observer {
            updatePost(it)
            if (it.feedId > 1) {
                binding.fabPrev.isEnabled = true
                binding.fabPrev.show()
            } else {
                binding.fabPrev.isEnabled = false
                binding.fabPrev.hide()
            }
        })

        viewModel.onLoadFailedEvent.observe(viewLifecycleOwner, Observer {
            if (it) onLoadFailed()
        })

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_feed_random,
            container,
            false
        )

        binding.fabNext.setOnClickListener {
            onNext()
        }

        binding.fabPrev.setOnClickListener {
            onPrevious()
        }

        // for image binding adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveStartingId()
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

    private fun onLoadFailed() {
        // nav to failure screen
        this.view?.let {
            findNavController().navigate(R.id.action_feedRandomFragment_to_errorFragment)
        }
    }
}