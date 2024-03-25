package com.den3000.androidmvxdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.den3000.androidmvxdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btViewMvc.setOnClickListener(this)
        binding.btViewMvp.setOnClickListener(this)
        binding.btViewMvvm.setOnClickListener(this)
        binding.btViewMvi.setOnClickListener(this)
        binding.btComposeMvvm.setOnClickListener(this)
        binding.btComposeMvi.setOnClickListener(this)
        binding.btBest.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btViewMvc -> println("btViewMvc")
            binding.btViewMvp -> println("btViewMvp")
            binding.btViewMvvm -> println("btViewMvvm")
            binding.btViewMvi -> println("btViewMvi")
            binding.btComposeMvvm -> println("btComposeMvvm")
            binding.btComposeMvi -> println("btComposeMvi")
            binding.btBest -> println("btBest")
        }
    }
}