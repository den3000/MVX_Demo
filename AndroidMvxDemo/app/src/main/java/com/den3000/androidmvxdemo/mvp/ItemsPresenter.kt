package com.den3000.androidmvxdemo.mvp

import com.den3000.androidmvxdemo.shared.ItemsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemsPresenter(private val view: IPresenterToView):
    IViewToPresenter,
    IPresenterToModel,
    IModelToPresenter
{
    private var model = ItemsModel()
    private var scope = CoroutineScope(context = Dispatchers.IO)
    private var textChangedJob: Job? = null

    init {
        view.progress(show = true)
        scope.launch {
            model.all()
            withContext(Dispatchers.Main) {
                updateList()
                view.progress(show = false)
            }
        }
    }

    //region ViewToPresenter
    override fun onClearPressed() {
        view.clearSearchText()
    }

    override fun onSearchTextChanged(cs: CharSequence?) {
        view.progress(show = true)

        textChangedJob?.cancel()
        textChangedJob = scope.launch {
            if (cs.isNullOrEmpty()) {
                resetModel()
            } else {
                filterModel(cs.toString())
            }

            withContext(Dispatchers.Main) {
                updateList()
                view.progress(show = false)
            }
        }
    }
    //endregion

    //region PresenterToModel
    override suspend fun resetModel() { model.all() }

    override suspend fun filterModel(text: String) { model.filter(text) }
    //endregion

    //region ModelToPresenter
    override fun modelDataset(): List<String> = model.dataset
    //endregion

    private fun updateList() {
        val dataset = modelDataset()
        view.update(dataset)
        view.results(display = dataset.isNotEmpty())
    }

    interface IPresenterToView {
        fun clearSearchText()
        fun progress(show: Boolean)
        fun results(display: Boolean)
        fun update(list: List<String>)
    }
}

private interface IViewToPresenter {
    fun onClearPressed()
    fun onSearchTextChanged(cs: CharSequence?)
}

private interface IPresenterToModel {
    suspend fun resetModel()
    suspend fun filterModel(text: String)
}

private interface IModelToPresenter {
    fun modelDataset(): List<String>
}