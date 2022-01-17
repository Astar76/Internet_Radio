package com.astar.myapplication.presentation.radiolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.astar.myapplication.R
import com.astar.myapplication.databinding.RadioListScreenBinding
import com.astar.myapplication.domain.model.Radio
import com.astar.myapplication.presentation.MainActivity
import com.astar.myapplication.presentation.control.RadioControlFragment

class RadioListFragment : Fragment() {

    private var _binding: RadioListScreenBinding? = null
    private val binding: RadioListScreenBinding get() = _binding!!
    private lateinit var viewModel: RadioListViewModel

    private val onItemRadioCallback: ((radio: Radio) -> Unit) = {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, RadioControlFragment(it.stream))
            .addToBackStack(null)
            .commit()
    }

    private val radioListAdapter = RadioListAdapter(onItemRadioCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as MainActivity).viewModel(RadioListViewModel::class.java, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = RadioListScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recycler) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = radioListAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.observe(viewLifecycleOwner, ::handleLoadRadioList)
    }

    private fun handleLoadRadioList(result: RadioResult) {
        when(result) {
            is RadioResult.Loading -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            is RadioResult.Success -> radioListAdapter.submitList(result.data)
            is RadioResult.Error -> Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
        }
    }
}