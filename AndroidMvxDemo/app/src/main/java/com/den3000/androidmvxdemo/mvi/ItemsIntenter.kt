package com.den3000.androidmvxdemo.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.den3000.androidmvxdemo.shared.ItemsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ItemsIntenter : ViewModel(),
        IIntenterToModel,
        IModelToIntenter
{
    private val model = ItemsModel()
    private var scope = CoroutineScope(context = Dispatchers.IO)
    private var textChangedJob: Job? = null

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
}

private interface IIntenterToModel {
    suspend fun resetModel()
    suspend fun filterModel(text: String)
}

private interface IModelToIntenter {
    fun modelDataset(): List<String>
}