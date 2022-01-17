package com.astar.myapplication.presentation.radiolist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astar.myapplication.R
import com.astar.myapplication.domain.model.Radio

class RadioViewHolder(
    view: View,
    private val callback: (radio: Radio) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.name)

    fun bind(radio: Radio) {
        name.text = radio.name
        itemView.setOnClickListener {
            callback.invoke(radio)
        }
    }
}