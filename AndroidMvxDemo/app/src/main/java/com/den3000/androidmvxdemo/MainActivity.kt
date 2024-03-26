package com.den3000.androidmvxdemo

import android.content.Intent
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
        binding.btViewMvx.setOnClickListener(this)
        binding.btComposeMvvm.setOnClickListener(this)
        binding.btComposeMvi.setOnClickListener(this)
        binding.btComposeMvx.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btViewMvc -> startActivity(Intent(this, ViewMvcActivity::class.java))
            binding.btViewMvp -> startActivity(Intent(this, ViewMvpActivity::class.java))
            binding.btViewMvvm -> startActivity(Intent(this, ViewMvvmActivity::class.java))
            binding.btViewMvi -> startActivity(Intent(this, ViewMviActivity::class.java))
            binding.btViewMvx -> startActivity(Intent(this, ViewMvxActivity::class.java))
            binding.btComposeMvvm -> startActivity(Intent(this, ComposeMvvmActivity::class.java))
            binding.btComposeMvi -> startActivity(Intent(this, ComposeMviActivity::class.java))
            binding.btComposeMvx -> startActivity(Intent(this, ComposeMvxActivity::class.java))
        }
    }
}