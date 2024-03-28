package com.den3000.androidmvxdemo.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.den3000.androidmvxdemo.shared.ItemsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemsViewModel : ViewModel() {

    private val model = ItemsModel()
    private var scope = CoroutineScope(context = Dispatchers.IO)
    private var textChangedJob: Job? = null

    val isShowProgress: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    val isShowResults: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    val searchText: MutableLiveData<String?> by lazy { MutableLiveData(null) }
    val dataset: MutableLiveData<List<String>> by lazy { MutableLiveData<List<String>>() }

    init {
        isShowProgress.value = true
        scope.launch {
            model.all()
            withContext(Dispatchers.Main) {
                updateList()
                isShowProgress.value = false
            }
        }
    }

    fun onClearPressed() {
        searchText.value = null
    }

    fun onSearchTextChanged(cs: CharSequence?) {
        textChangedJob?.cancel()
        isShowProgress.value = true
        textChangedJob = scope.launch {
            if (cs.isNullOrEmpty()) {
                model.all()
            } else {
                model.filter(cs.toString())
            }

            withContext(Dispatchers.Main) {
                updateList()
                isShowProgress.value = false
            }
        }
    }

    private fun updateList() {
        dataset.value = model.dataset
        isShowResults.value = dataset.value?.isNotEmpty() ?: false
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // DI goes here
                ItemsViewModel()
            }
        }
    }
}