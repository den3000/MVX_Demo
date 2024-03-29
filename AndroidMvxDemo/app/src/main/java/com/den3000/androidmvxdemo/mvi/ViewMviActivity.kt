package com.den3000.androidmvxdemo.mvi

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.den3000.androidmvxdemo.databinding.ActivityViewBinding
import com.den3000.androidmvxdemo.shared.ItemsAdapter

class ViewMviActivity : AppCompatActivity(),
    View.OnClickListener,
    TextWatcher
{

    private lateinit var binding: ActivityViewBinding

    private var adapter: ItemsAdapter? = null
    private val intenter: ItemsIntenter by viewModels { ItemsIntenter.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvTitle.text = "View MVI"
        binding.btClearSearch.setOnClickListener(this)
        binding.etSearchString.addTextChangedListener(this)

        adapter = ItemsAdapter(emptyList())
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = adapter

        setupObservers()
    }

    private fun setupObservers() {
        intenter.viewState.observe(this) {
            binding.piSearch.visibility = if (it.isShowProgress) View.VISIBLE else View.INVISIBLE

            if (it.isShowResults) {
                binding.tvNoResults.visibility = View.GONE
                binding.rvItems.visibility = View.VISIBLE
            } else {
                binding.tvNoResults.visibility = View.VISIBLE
                binding.rvItems.visibility = View.GONE
            }

            binding.etSearchString.text = SpannableStringBuilder(it.searchText)

            adapter?.dataSet = it.dataset
        }
    }

    //region TextWatcher
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {
        intenter.obtain(ItemsIntenter.ViewEvent.TextChanged(cs?.toString()))
    }

    override fun afterTextChanged(p0: Editable?) { }
    //endregion

    //region View.OnClickListener
    override fun onClick(view: View?) {
        when(view) {
            binding.btClearSearch -> {
                intenter.obtain(ItemsIntenter.ViewEvent.ClearText)
            }
        }
    }
    //endregion
}