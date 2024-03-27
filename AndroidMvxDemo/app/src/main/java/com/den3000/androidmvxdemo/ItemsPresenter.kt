package com.den3000.androidmvxdemo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ItemsPresenter(private val view: IView):
    IPresenterToModel,
    IModelToPresenter
{

    private var model = ItemsModel()
    private var scope = CoroutineScope(context = Dispatchers.IO)
    private var textChangedJob: Job? = null

    fun start() {
        view.progress(show = true)
        scope.launch {
            model.all()
            updateList()
        }
    }

    fun onClearPressed() {
        view.clearSearchText()
    }

    fun onSearchTextChanged(cs: CharSequence?) {
        textChangedJob?.cancel()
        view.progress(show = true)
        textChangedJob = scope.launch {
            if (cs.isNullOrEmpty()) {
                resetModel()
            } else {
                filterModel(cs.toString())
            }
            updateList()
        }
    }

    private suspend fun updateList() {
        withContext(Dispatchers.Main) {
            val dataset = modelDataset()
            view.update(dataset)
            view.results(display = dataset.isNotEmpty())
            view.progress(show = true)
        }
    }

    interface IView {
        fun clearSearchText()
        fun progress(show: Boolean)
        fun results(display: Boolean)
        fun update(list: List<String>)
    }

    //region PresenterToModel
    override suspend fun resetModel() { model.all() }

    override suspend fun filterModel(text: String) { model.filter(text) }
    //endregion

    //region ModelToPresenter
    override fun modelDataset(): List<String> = model.dataset
    //endregion
}

private interface IPresenterToModel {
    suspend fun resetModel()
    suspend fun filterModel(text: String)
}

private interface IModelToPresenter {
    fun modelDataset(): List<String>
}