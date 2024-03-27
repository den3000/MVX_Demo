package com.den3000.androidmvxdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.den3000.androidmvxdemo.databinding.ActivityViewMvcBinding

class ViewMvcActivity : AppCompatActivity(), TextWatcher, View.OnClickListener {

    private var model = ItemsModel()
    private lateinit var binding: ActivityViewMvcBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMvcBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvTitle.text = "View MVC"
        binding.btClearSearch.setOnClickListener(this)
        binding.etSearchString.addTextChangedListener(this)

        with(binding.rvItems) {
            layoutManager = LinearLayoutManager(this@ViewMvcActivity)
            adapter = ItemsAdapter(model.all())
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {
        println("$cs $p1 $p2 $p3")
    }

    override fun afterTextChanged(p0: Editable?) { }

    override fun onClick(view: View?) {
        when(view) {
            binding.btClearSearch -> binding.etSearchString.text = null
        }
    }
}