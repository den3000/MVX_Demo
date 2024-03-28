package com.den3000.androidmvxdemo.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.den3000.androidmvxdemo.shared.ItemsModel

class ItemsViewModel : ViewModel() {

    private var model = ItemsModel()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // DI goes here
                ItemsViewModel()
            }
        }
    }
}