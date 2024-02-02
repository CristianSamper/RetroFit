package com.example.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.DigiViewHolder
import com.example.retrofit.R

class DigiAdapter(private val images: List<String>) : RecyclerView.Adapter<DigiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DigiViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DigiViewHolder(layoutInflater.inflate(R.layout.item_digi, parent, false))
    }

    override fun getItemCount(): Int = images.size


    override fun onBindViewHolder(holder: DigiViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }
}