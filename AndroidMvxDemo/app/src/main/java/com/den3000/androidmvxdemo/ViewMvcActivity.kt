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

    private lateinit var binding: ActivityViewMvcBinding

    private var model = ItemsModel()
    private var adapter: ItemsAdapter? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMvcBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = ItemsAdapter(model.all())

        binding.tvTitle.text = "View MVC"
        binding.btClearSearch.setOnClickListener(this)
        binding.etSearchString.addTextChangedListener(this)

        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = adapter
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (cs.isNullOrEmpty()) {
            adapter?.dataSet = model.all()
        } else {
            adapter?.dataSet = model.filter(cs.toString())
        }
    }

    override fun afterTextChanged(p0: Editable?) { }

    override fun onClick(view: View?) {
        when(view) {
            binding.btClearSearch -> binding.etSearchString.text = null
        }
    }
}