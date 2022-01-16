package com.astar.myapplication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astar.myapplication.R
import com.astar.myapplication.core.Result
import com.astar.myapplication.data.BaseRadioRepository
import com.astar.myapplication.data.local.AppDatabase
import com.astar.myapplication.data.local.RadioDao
import com.astar.myapplication.data.local.RadioLocalDataSource
import com.astar.myapplication.data.remote.RadioRemoteDataSource
import com.astar.myapplication.data.remote.RetrofitClient
import com.astar.myapplication.data.remote.service.RadioServiceApi
import com.astar.myapplication.domain.RadioRepository
import com.astar.myapplication.domain.model.Radio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RadioListFragment : Fragment() {

    private lateinit var recycler: RecyclerView

    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.radio_list_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioListAdapter = RadioListAdapter(onItemRadioCallback)
        recycler = view.findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = radioListAdapter

        coroutineScope.launch {
            val radioDao: RadioDao = AppDatabase.getInstance(requireContext()).radioDao()
            val localDataSource: RadioLocalDataSource = RadioLocalDataSource.Base(radioDao)
            val serviceApi: RadioServiceApi = RetrofitClient.create()
            val remoteDataSource: RadioRemoteDataSource = RadioRemoteDataSource.Base(serviceApi)
            val repository: RadioRepository = BaseRadioRepository(remoteDataSource, localDataSource)

            when(val result = repository.fetchAll()) {
                is Result.Success -> radioListAdapter.submitList(result.result)
                is Result.Error -> Toast.makeText(requireContext(), result.result.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val onItemRadioCallback: ((radio: Radio) -> Unit) = {
        Toast.makeText(requireContext(), "radio - ${it.name}", Toast.LENGTH_SHORT).show()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, RadioControlFragment(it.stream))
            .addToBackStack(null)
            .commit()
    }
}