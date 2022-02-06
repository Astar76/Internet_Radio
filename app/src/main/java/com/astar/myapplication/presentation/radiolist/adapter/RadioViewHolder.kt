package com.astar.myapplication.presentation.radiolist.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astar.myapplication.R
import com.astar.myapplication.domain.model.Radio
import com.bumptech.glide.Glide

class RadioViewHolder(
    view: View,
    private val callback: RadioListAdapter.Callback
) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.name)
    private val icon: ImageView = view.findViewById(R.id.icon)
    private val playPause: View = view.findViewById(R.id.playPause)

    fun bind(radio: Radio) {
        name.text = radio.name
        Glide.with(itemView.context).load(radio.image).into(icon)
        itemView.setOnClickListener {
            callback.onClickForDetails(radio)
        }
        icon.setOnClickListener {
            callback.onClickForPlay(radio)
        }
    }
}