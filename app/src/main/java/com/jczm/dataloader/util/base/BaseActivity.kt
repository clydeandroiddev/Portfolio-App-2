package com.jczm.dataloader.util.base

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jczm.dataloader.R
import com.jczm.dataloader.util.helper.LocaleUtilHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


abstract class BaseActivity<T : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {

    abstract val layout: Int
    abstract val viewModel: T
    lateinit var binding: B



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                top = systemBars.top,
                bottom = systemBars.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
        initViews()
        observe()
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base?.let { LocaleUtilHelper(it).setLocale(base) })
    }



    override fun onPause() {
        super.onPause()
    }

    open fun initViews() {

    }

    open fun observe() {

    }

    fun close() {
        finish()
    }
}