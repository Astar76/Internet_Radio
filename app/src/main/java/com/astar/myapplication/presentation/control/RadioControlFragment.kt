package com.astar.myapplication.presentation.control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astar.myapplication.databinding.FragmentRadioControlBinding
import com.astar.myapplication.domain.model.Radio
import com.astar.myapplication.presentation.base.BaseFragment

class RadioControlFragment : BaseFragment<FragmentRadioControlBinding, RadioControlViewModel>() {

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentRadioControlBinding.inflate(inflater, container, false)

    override fun viewModelClass() = RadioControlViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.play.setOnClickListener { playRadio() }
        binding.stop.setOnClickListener { stopRadio() }
    }

    private fun playRadio() {
        viewModel.changePlayingStatus()
    }

    private fun stopRadio() {
        viewModel.changePlayingStatus()
    }

    companion object {

        private const val STREAM_URL = "stream_url"
        private const val NAME = "name"
        private const val IMAGE = "image"
        private const val GENRE = "genre"

        @JvmStatic
        fun newInstance(radio: Radio) = RadioControlFragment().apply {
            arguments = Bundle().apply {
                putString(NAME, radio.name)
                putString(STREAM_URL, radio.stream)
            }
        }
    }
}