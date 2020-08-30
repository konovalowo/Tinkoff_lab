package com.example.tinkoff_lab.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.tinkoff_lab.R
import com.example.tinkoff_lab.databinding.FragmentErrorBinding

class ErrorFragment : Fragment() {

    private lateinit var binding: FragmentErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_error,
            container,
            false
        )

        binding.tryAgainButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_errorFragment_to_feedRandomFragment)
        }

        return binding.root
    }
}