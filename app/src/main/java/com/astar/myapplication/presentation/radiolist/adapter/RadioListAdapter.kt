package com.astar.myapplication.presentation.radiolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.astar.myapplication.R
import com.astar.myapplication.domain.model.Radio

class RadioListAdapter(
    private val callback: (radio: Radio) -> Unit
) : ListAdapter<Radio, RadioViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.radio_item_layout, parent, false)
        return RadioViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: RadioViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Radio>() {
            override fun areItemsTheSame(oldItem: Radio, newItem: Radio): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Radio, newItem: Radio): Boolean {
                return oldItem == newItem
            }
        }
    }
}
