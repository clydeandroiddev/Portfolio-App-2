package com.jczm.dataloader.ui.view

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.jczm.dataloader.R
import com.jczm.dataloader.data.model.SortType
import com.jczm.dataloader.databinding.ActivityMainBinding
import com.jczm.dataloader.ui.adapter.CarListAdapter
import com.jczm.dataloader.util.base.BaseActivity
import com.jczm.dataloader.util.constants.Values
import com.jczm.dataloader.util.extensions.gone
import com.jczm.dataloader.util.extensions.onLayoutLoadingComplete
import com.jczm.dataloader.util.extensions.readJsonFromAssets
import com.jczm.dataloader.util.extensions.recreateSmoothly
import com.jczm.dataloader.util.extensions.visible
import com.jczm.dataloader.util.helper.DebouncingQueryTextListener
import com.jczm.dataloader.util.helper.LangEnum
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), SortBottomSheetDialog.SortBottomSheetListener {
    override val layout: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    private var sortBottomSheetDialog : SortBottomSheetDialog? = null

    private var currSelectedSortPos = 0
    private var currSortType : SortType = SortType.PRIORTY_ASC


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(viewModel.hasCarData()){
            viewModel.fetchCarsData()
        }else {
            val carJson = this@MainActivity.readJsonFromAssets(Values.CARS_FILE_NAME)
            viewModel.storeCarsData(carJson)
        }
    }

    private val carListAdapter : CarListAdapter by lazy {
        CarListAdapter()
    }

    override fun initViews() {
        super.initViews()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        setupCarListView()
        setupFab()
    }

    private fun setupFab() {
        binding.fabSort.apply {
            hide()
            setOnClickListener {
                sortBottomSheetDialog?.dialog?.let {
                    if(it.isShowing){
                        return@setOnClickListener
                    }
                }
                sortBottomSheetDialog = SortBottomSheetDialog.newInstance(currSelectedSortPos)
                sortBottomSheetDialog?.show(supportFragmentManager, SortBottomSheetDialog::class.java.simpleName)

            }
        }

    }

    override fun observe() {
        super.observe()

        viewModel.proceed.observe(this){
            it.getContentIfNotHandled()?.let{
                viewModel.fetchCarsData()
            }
        }

        viewModel.showError.observe(this){
            it.getContentIfNotHandled()?.let {errorMsg ->
                showEmptyState()
                binding.tvEmptyState.text = errorMsg
            }
        }

        viewModel.showCarLists.observe(this){
            it.getContentIfNotHandled()?.let {carLists ->
                if(carLists.isNotEmpty()){
                    binding.apply {
                        rvCars.onLayoutLoadingComplete {
                            hideShimmer()
                        }
                    }
                    carListAdapter.update(carLists)
                }else {
                    showEmptyState()
                }

            }
        }
    }

    private fun setupCarListView(){
        binding.rvCars.apply {
            adapter = carListAdapter
            setHasFixedSize(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val itemToggleButton = menu?.findItem(R.id.action_language)
        itemToggleButton?.apply {
            setActionView(R.layout.menu_language)
            val toggleBtnGroup = actionView?.findViewById<MaterialButtonToggleGroup>(R.id.toggleButton)
            val language = Locale.getDefault().language
            toggleBtnGroup?.apply {
                if(language == LangEnum.ar.name){
                    check(R.id.btn_ar)
                }else {
                    check(R.id.btn_en)
                }

                addOnButtonCheckedListener { materialButtonToggleGroup, checkedId, isChecked ->
                    if(checkedId == R.id.btn_ar && isChecked){
                        viewModel.updateLanguage(LangEnum.ar.name)
                    }else {
                        viewModel.updateLanguage(LangEnum.en.name)
                    }
                    this@MainActivity.recreateSmoothly()
                }
            }

        }
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.let {
            val searchView : SearchView = searchItem.actionView as SearchView
            val closeIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
            closeIcon.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
            val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            searchEditText.setTextColor(Color.WHITE)
            searchEditText.setHintTextColor(Color.WHITE)

            searchView.setOnQueryTextListener(DebouncingQueryTextListener(this@MainActivity.lifecycle){
                newText ->
                newText?.let {
                    if(it.isEmpty()){
                        viewModel.fetchCarsDataWith(currSortType)
                    }else{
                        showShimmer()
                        viewModel.searchCarsDataWith(it, currSortType)
                    }
                }
            })
        }
        return true;
    }

    override fun onApply(sortType: SortType, position: Int) {
        currSelectedSortPos = position
        currSortType = sortType
        sortBottomSheetDialog?.dismissAllowingStateLoss()
        showShimmer()
        viewModel.fetchCarsDataWith(sortType)
    }

    private fun showShimmer(){
        binding.apply {
            rvCars.gone()
            fabSort.hide()
            fmPlaceholder.visible()
            lnEmptyState.gone()
        }
    }

    private fun hideShimmer(){
        binding.apply {
            rvCars.visible()
            fabSort.show()
            fmPlaceholder.gone()
            lnEmptyState.gone()
        }
    }

    private fun showEmptyState() {
        binding.apply {
            rvCars.gone()
            fabSort.hide()
            fmPlaceholder.gone()
            lnEmptyState.visible()
        }
    }
}