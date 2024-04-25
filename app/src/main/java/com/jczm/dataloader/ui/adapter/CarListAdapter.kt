package com.jczm.dataloader.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jczm.dataloader.R
import com.jczm.dataloader.data.model.CarData
import com.jczm.dataloader.databinding.ItemCarBinding
import com.jczm.dataloader.ui.viewholder.CarListViewHolder

class CarListAdapter(private var lists : List<CarData> = arrayListOf()) : RecyclerView.Adapter<CarListViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListViewHolder {
        val binding : ItemCarBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_car,
            parent,
            false
        )
        return CarListViewHolder(binding)
    }

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: CarListViewHolder, position: Int) {
        val data = lists[position]
        holder.binding.apply {
            car = data
            executePendingBindings()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(lists : List<CarData>){
        this.lists = lists
        notifyDataSetChanged()
    }
}