package com.den3000.androidmvxdemo.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.den3000.androidmvxdemo.R

class ViewMvvmActivity : AppCompatActivity() {

    private val viewModel: ItemsViewModel by viewModels { ItemsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_mvvm)
    }
}