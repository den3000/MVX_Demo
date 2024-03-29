package com.den3000.androidmvxdemo.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.den3000.androidmvxdemo.ui.theme.AndroidMvxDemoTheme

@Composable
fun ComposeMvvmView(
    title: String,
    isShowProgress: Boolean,
    isShowResults: Boolean,
    text: String,
    onTextChanged: (String) -> Unit,
    onClear: () -> Unit,
    dataset: List<String>,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (refTitle, refProgress, refSearch, refClear, refNoResults, refItems) = createRefs()

        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.constrainAs(refTitle) {
                width = Dimension.fillToConstraints
                height = Dimension.value(40.dp)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(parent.top, margin = 16.dp)
            }
                .wrapContentHeight(),

            )

        if (isShowProgress) {
            CircularProgressIndicator(
                modifier = modifier.constrainAs(refProgress) {
                    width = Dimension.value(40.dp)
                    height = Dimension.value(40.dp)
                    end.linkTo(refTitle.end)
                    top.linkTo(refTitle.top)
                }
            )
        }

        TextField(
            value = text,
            modifier = modifier.constrainAs(refSearch) {
                width = Dimension.fillToConstraints
                height = Dimension.value(40.dp)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(refTitle.bottom, margin = 16.dp)
            },
            onValueChange = onTextChanged
        )

        Button(
            modifier = modifier.constrainAs(refClear) {
                width = Dimension.fillToConstraints
                height = Dimension.value(48.dp)
                end.linkTo(parent.end, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                top.linkTo(refSearch.bottom, margin = 16.dp)
            },
            onClick = onClear
        ) {
            Text("Clear")
        }

        if (isShowResults) {
            LazyColumn(
                modifier = modifier.constrainAs(refItems) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(refClear.bottom, margin = 16.dp)
                }
            ) {
                items(dataset) {
                    Text(
                        text = it,
                        textAlign = TextAlign.Start,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .wrapContentHeight(),
                    )
                }
            }
        } else {
            Text(
                text = "No Results",
                textAlign = TextAlign.Center,
                modifier = modifier.constrainAs(refNoResults) {
                    width = Dimension.fillToConstraints
                    height = Dimension.value(40.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(refClear.bottom, margin = 16.dp)
                }
                    .wrapContentHeight(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeMvvmViewPreview() {
    AndroidMvxDemoTheme {
        ComposeMvvmView(
            title = "Compose MVVM",
            isShowProgress = true,
            isShowResults = true,
            text = "",
            onTextChanged = {},
            onClear = {},
            dataset = listOf("Item 1", "Item 2",  "Item 3",  "Item 4",  "Item 5",  "Item 6")
        )
    }
}

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
                    ComposeMvvmView(
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