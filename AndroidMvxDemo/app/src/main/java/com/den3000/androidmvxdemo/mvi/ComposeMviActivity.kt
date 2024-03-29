package com.den3000.androidmvxdemo.mvi

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

class ComposeMviActivity : ComponentActivity() {

    private val intenter: ItemsIntenter by viewModels { ItemsIntenter.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewState = intenter.viewState.observeAsState(initial = ItemsIntenter.ViewState(
                isShowProgress = false,
                isShowResults = true,
                searchText = "",
                dataset = emptyList()
            ))

            AndroidMvxDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComposeView(
                        title = "Compose MVVM",
                        isShowProgress = viewState.value.isShowProgress,
                        isShowResults = viewState.value.isShowResults,
                        text = viewState.value.searchText,
                        onTextChanged = { intenter.obtain(ItemsIntenter.ViewEvent.TextChanged(it)) },
                        onClear = { intenter.obtain(ItemsIntenter.ViewEvent.ClearText) },
                        dataset = viewState.value.dataset
                    )
                }
            }
        }
    }
}