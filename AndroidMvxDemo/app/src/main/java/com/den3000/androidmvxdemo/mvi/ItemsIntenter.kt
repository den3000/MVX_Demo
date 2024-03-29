package com.den3000.androidmvxdemo.mvi

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

class ItemsIntenter : ViewModel(),
        IIntenterToModel,
        IModelToIntenter
{
    private val model = ItemsModel()
    private var scope = CoroutineScope(context = Dispatchers.IO)
    private var textChangedJob: Job? = null

    val viewState: MutableLiveData<ViewState> by lazy { MutableLiveData(ViewState(
        isShowProgress = false,
        isShowResults = true,
        searchText = "",
        dataset = emptyList()
    )) }

    init {
        viewState.value = viewState.value?.copy(isShowProgress = true)
        scope.launch {
            resetModel()
            withContext(Dispatchers.Main) {
                val dataset = modelDataset()
                viewState.value = viewState.value?.copy(
                    isShowProgress = false,
                    isShowResults = dataset.isNotEmpty(),
                    dataset = dataset
                )
            }
        }
    }

    fun obtain(event: ViewEvent) {
        when (event) {
            ViewEvent.ClearText -> {
                // State reducer
                if (viewState.value?.searchText == "") { return }

                viewState.value = viewState.value?.copy(searchText = "")
            }

            is ViewEvent.TextChanged -> {
                // State reducer
                if (viewState.value?.searchText == event.text) { return }

                viewState.value = viewState.value?.copy(
                    isShowProgress = true,
                    searchText = event.text ?: ""
                )

                textChangedJob?.cancel()
                textChangedJob = scope.launch {
                    if (event.text.isNullOrEmpty()) {
                        resetModel()
                    } else {
                        filterModel(event.text)
                    }

                    withContext(Dispatchers.Main) {
                        val dataset = modelDataset()
                        viewState.value = viewState.value?.copy(
                            isShowProgress = false,
                            isShowResults = dataset.isNotEmpty(),
                            dataset = dataset
                        )
                    }
                }
            }
        }
    }

    //region IIntenterToModel
    override suspend fun resetModel() { model.all() }

    override suspend fun filterModel(text: String) { model.filter(text) }
    //endregion

    //region IModelToIntenter
    override fun modelDataset(): List<String> = model.dataset
    //endregion

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // DI goes here
                ItemsIntenter()
            }
        }
    }

    data class ViewState(
        val isShowProgress: Boolean,
        val isShowResults: Boolean,
        val searchText: String,
        val dataset: List<String>,
    )

    sealed class ViewEvent {
        data class TextChanged(val text: String?): ViewEvent()
        data object ClearText: ViewEvent()
    }
}

private interface IIntenterToModel {
    suspend fun resetModel()
    suspend fun filterModel(text: String)
}

private interface IModelToIntenter {
    fun modelDataset(): List<String>
}