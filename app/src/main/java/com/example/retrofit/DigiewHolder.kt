package com.example.retrofit

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.databinding.ItemDigiBinding
import com.squareup.picasso.Picasso

class DigiViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemDigiBinding.bind(view)

    fun bind(image:String){
        Picasso.get().load(image).into(binding.ivDigi)
    }
}