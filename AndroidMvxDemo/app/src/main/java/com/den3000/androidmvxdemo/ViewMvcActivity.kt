package com.den3000.androidmvxdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.den3000.androidmvxdemo.databinding.ActivityViewMvcBinding

class ViewMvcActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewMvcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMvcBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvTitle.text = "View MVC"
    }
}