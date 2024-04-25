package com.jczm.dataloader.util.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jczm.dataloader.util.helper.Coroutines
import kotlinx.coroutines.CoroutineExceptionHandler

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {


    fun invoke(task: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            task.invoke()
        }
    }

    fun invokeAtBackground(task: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { task.invoke() }
    }

}