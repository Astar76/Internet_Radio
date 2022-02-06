package com.astar.myapplication.presentation.radiolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astar.myapplication.R
import com.astar.myapplication.databinding.RadioListScreenBinding
import com.astar.myapplication.domain.model.Radio
import com.astar.myapplication.presentation.base.BaseFragment
import com.astar.myapplication.presentation.control.RadioControlFragment
import com.astar.myapplication.presentation.radiolist.adapter.RadioListAdapter

class RadioListFragment : BaseFragment<RadioListScreenBinding, RadioListViewModel>(),
    RadioListAdapter.Callback {

    private val radioListAdapter = RadioListAdapter(this)

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
        RadioListScreenBinding.inflate(layoutInflater, container, false)

    override fun viewModelClass(): Class<RadioListViewModel> = RadioListViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerRadio) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(createItemDecoration())
            adapter = radioListAdapter
        }

        binding.playPauseButton.setOnClickListener { viewModel.changePlayingState() }
        binding.favorite.setOnClickListener { toast("Список избранных радиостанций") }
        binding.exit.setOnClickListener { requireActivity().finish() }

        viewModel.observeRadioList(viewLifecycleOwner, ::handleLoadRadioList)
        viewModel.observePlayingState(viewLifecycleOwner, ::updatePlayPauseButton)
    }

    private fun updatePlayPauseButton(isPlaying: Boolean) {
        binding.playPauseButton.setImageResource(if (isPlaying) R.drawable.icon_stop else R.drawable.icon_play)
    }

    private fun createItemDecoration(): RecyclerView.ItemDecoration {
        return DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
    }

    private fun handleLoadRadioList(result: RadioResult) {
        when (result) {
            is RadioResult.Loading -> binding.loading.isVisible = true
            is RadioResult.Success -> showRadioList(result.data)
            is RadioResult.Error -> showError(result.message)
        }
    }

    private fun showRadioList(radioList: List<Radio>) {
        binding.loading.isVisible = false
        radioListAdapter.submitList(radioList)
    }

    private fun showError(message: String) {
        binding.loading.isVisible = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onClickForPlay(radio: Radio) {
        viewModel.playRadio(radio)
    }

    override fun onClickForDetails(radio: Radio) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, RadioControlFragment.newInstance(radio))
            .addToBackStack(null)
            .commit()
    }
}