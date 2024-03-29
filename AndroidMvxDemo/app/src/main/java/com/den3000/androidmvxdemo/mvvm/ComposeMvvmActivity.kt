package com.den3000.androidmvxdemo.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.den3000.androidmvxdemo.shared.ComposeView
import com.den3000.androidmvxdemo.ui.theme.AndroidMvxDemoTheme

class ComposeMvvmActivity : ComponentActivity() {

    private val viewModel: ItemsViewModel by viewModels { ItemsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isShowProgress = viewModel.isShowProgress.observeAsState(initial = false)
            val isShowResults = viewModel.isShowResults.observeAsState(initial = true)
            val searchText = viewModel.searchText.observeAsState(initial = "")
            val dataset = viewModel.dataset.observeAsState(initial = emptyList())

            AndroidMvxDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComposeView(
                        title = "Compose MVVM",
                        isShowProgress = isShowProgress.value,
                        isShowResults = isShowResults.value,
                        text = searchText.value,
                        onTextChanged = { viewModel.onSearchTextChanged(it) },
                        onClear = { viewModel.onClearPressed() },
                        dataset = dataset.value
                    )
                }
            }
        }
    }
}