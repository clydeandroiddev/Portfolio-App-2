package com.jczm.dataloader.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jczm.dataloader.R
import com.jczm.dataloader.data.model.SortData
import com.jczm.dataloader.data.model.SortType
import com.jczm.dataloader.databinding.BottomSheetSortBinding
import com.jczm.dataloader.ui.adapter.SortListAdapter
import com.jczm.dataloader.ui.viewholder.SortListViewHolder
import com.jczm.dataloader.util.constants.Values


class SortBottomSheetDialog() : BottomSheetDialogFragment(), SortListViewHolder.SortListListener {

    private lateinit var binding : BottomSheetSortBinding
    private var currentSelectedPos = 0
    private var sortBottomSheetListener : SortBottomSheetListener? = null
    private var selectedSortData : SortData? = null
    private val sortListAdapter : SortListAdapter by lazy {
        SortListAdapter(listener = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)
        arguments?.let {
            currentSelectedPos = it.getInt(Values.KEY_CURRENT_SELECTED_POS, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            ibClose.setOnClickListener {
                dismissAllowingStateLoss()
            }
            btnApply.setOnClickListener {
                selectedSortData?.let {
                    sortBottomSheetListener?.onApply(it.value, currentSelectedPos)
                }
            }
        }
        setupSortListRecyclerView()
    }

    private fun setupSortListRecyclerView() {
        binding.rvSort.apply {
            adapter = sortListAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
        val sortlist = arrayListOf<SortData>()
        sortlist.add(SortData("Priority: High to Low", SortType.PRIORTY_ASC, false))
        sortlist.add(SortData("Priority: Low to High", SortType.PRIORITY_DESC, false))
        sortlist.add(SortData("Price: High to Low", SortType.PRICE_ASC, false))
        sortlist.add(SortData("Price: Low to High", SortType.PRICE_DESC, false))
        sortlist.add(SortData("Bids", SortType.BIDS, false))
        sortlist[currentSelectedPos].isSelected = true
        sortListAdapter.update(sortlist)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            sortBottomSheetListener = context as SortBottomSheetListener?
        } catch (e: ClassCastException) {
        }
    }

    interface SortBottomSheetListener {
        fun onApply(sortType: SortType, position : Int)
    }

    companion object {

        fun newInstance(currentSelectedPos : Int) = SortBottomSheetDialog().apply {
            arguments = Bundle().apply {
                putInt(Values.KEY_CURRENT_SELECTED_POS, currentSelectedPos)
            }
        }
    }

    override fun onSelectSortData(sortData: SortData, position: Int) {
        val prevSelectedPos = currentSelectedPos
        selectedSortData = sortData
        currentSelectedPos = position
        sortListAdapter.updateSelectedItem(prevSelectedPos, currentSelectedPos)
    }


}