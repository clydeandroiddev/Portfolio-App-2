package com.jczm.dataloader.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jczm.dataloader.data.model.SortData
import com.jczm.dataloader.databinding.ItemSortBinding

class SortListViewHolder(val binding : ItemSortBinding) : RecyclerView.ViewHolder(binding.root) {

    interface SortListListener {
        fun onSelectSortData(sortData: SortData, position : Int)
    }

}