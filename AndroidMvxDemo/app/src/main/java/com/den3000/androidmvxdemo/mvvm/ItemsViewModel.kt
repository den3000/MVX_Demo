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

class ItemsViewModel : ViewModel(),
    IViewModelToView,
    IViewToViewModel,
    IViewModelToModel,
    IModelToViewModel
{

    private val model = ItemsModel()
    private var scope = CoroutineScope(context = Dispatchers.IO)
    private var textChangedJob: Job? = null

    val isShowProgress: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    val isShowResults: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    val searchText: MutableLiveData<String?> by lazy { MutableLiveData(null) }
    val dataset: MutableLiveData<List<String>> by lazy { MutableLiveData<List<String>>() }

    init {
        progress(show = true)
        scope.launch {
            resetModel()
            withContext(Dispatchers.Main) {
                updateList()
                progress(show = false)
            }
        }
    }

    //region ViewToViewModel
    override fun onClearPressed() {
        clearSearchText()
    }

    override fun onSearchTextChanged(cs: CharSequence?) {
        textChangedJob?.cancel()
        progress(show = true)
        textChangedJob = scope.launch {
            if (cs.isNullOrEmpty()) {
                resetModel()
            } else {
                filterModel(cs.toString())
            }

            withContext(Dispatchers.Main) {
                updateList()
                progress(show = false)
            }
        }
    }
    //endregion

    //region ViewModelToView
    override fun clearSearchText() { searchText.value = null }

    override fun progress(show: Boolean) { isShowProgress.value = show }

    override fun results(list: List<String>?) {
        dataset.value = list
        isShowResults.value = list?.isNotEmpty() ?: false
    }
    //endregion

    //region IViewModelToModel
    override suspend fun resetModel() { model.all() }

    override suspend fun filterModel(text: String) { model.filter(text) }
    //endregion

    //region IModelToViewModel
    override fun modelDataset(): List<String> = model.dataset
    //endregion

    private fun updateList() { results(modelDataset()) }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // DI goes here
                ItemsViewModel()
            }
        }
    }
}

private interface IViewModelToView {
    fun clearSearchText()
    fun progress(show: Boolean)
    fun results(list: List<String>?)
}

private interface IViewToViewModel {
    fun onClearPressed()
    fun onSearchTextChanged(cs: CharSequence?)
}

private interface IViewModelToModel {
    suspend fun resetModel()
    suspend fun filterModel(text: String)
}

private interface IModelToViewModel {
    fun modelDataset(): List<String>
}