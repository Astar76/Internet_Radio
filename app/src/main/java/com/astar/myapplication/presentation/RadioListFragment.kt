package com.astar.myapplication.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astar.myapplication.R
import com.astar.myapplication.data.RadioDataSource
import com.astar.myapplication.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RadioListFragment : Fragment() {

    private val radioListAdapter = RadioListAdapter()
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

        recycler = view.findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = radioListAdapter

        coroutineScope.launch {
            val dataSource: RadioDataSource = RadioDataSource.Base(RetrofitClient.create())
            radioListAdapter.update(dataSource.fetchPopular().map { it.name })
        }
    }
}