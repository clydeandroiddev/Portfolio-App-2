package com.jczm.dataloader.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jczm.dataloader.R
import com.jczm.dataloader.data.model.SortData
import com.jczm.dataloader.databinding.ItemSortBinding
import com.jczm.dataloader.ui.view.SortBottomSheetDialog
import com.jczm.dataloader.ui.viewholder.SortListViewHolder

class SortListAdapter(
    var lists: List<SortData> = arrayListOf(),
    private val listener: SortListViewHolder.SortListListener
) : RecyclerView.Adapter<SortListViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortListViewHolder {
        val binding : ItemSortBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_sort,
            parent,
            false
        )
        return SortListViewHolder(binding)
    }

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: SortListViewHolder, position: Int) {
        val data = lists[position]
        holder.apply {
            binding.apply {
                sort = data
                executePendingBindings()
            }
            itemView.setOnClickListener {
                listener.onSelectSortData(data, position)
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(list : List<SortData>){
        lists = list
        notifyDataSetChanged()
    }

    fun updateSelectedItem(prevPos : Int, currPos : Int){
        lists[prevPos].isSelected = false
        lists[currPos].isSelected = true
        notifyItemChanged(prevPos)
        notifyItemChanged(currPos)
    }

}