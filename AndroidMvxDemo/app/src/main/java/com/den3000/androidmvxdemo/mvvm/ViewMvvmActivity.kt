package com.den3000.androidmvxdemo.mvvm

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

class ViewMvvmActivity : AppCompatActivity(),
    View.OnClickListener,
    TextWatcher
{
    private lateinit var binding: ActivityViewBinding

    private var adapter: ItemsAdapter? = null
    private val viewModel: ItemsViewModel by viewModels { ItemsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvTitle.text = "View MVVM"
        binding.btClearSearch.setOnClickListener(this)
        binding.etSearchString.addTextChangedListener(this)

        adapter = ItemsAdapter(emptyList())
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = adapter

        setupVmObservers()
    }

    private fun setupVmObservers() {
        viewModel.isShowProgress.observe(this) {
            binding.piSearch.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }

        viewModel.isShowResults.observe(this) {
            if (it) {
                binding.tvNoResults.visibility = View.GONE
                binding.rvItems.visibility = View.VISIBLE
            } else {
                binding.tvNoResults.visibility = View.VISIBLE
                binding.rvItems.visibility = View.GONE
            }
        }

        viewModel.searchText.observe(this) {
            binding.etSearchString.text = SpannableStringBuilder(it)
            if (binding.etSearchString.isFocused) {
                binding.etSearchString.setSelection(it.length)
            }
        }

        viewModel.dataset.observe(this) {
            adapter?.dataSet = it
        }
    }

    //region TextWatcher
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {
        viewModel.onSearchTextChanged(cs.toString())
    }

    override fun afterTextChanged(p0: Editable?) { }
    //endregion

    //region View.OnClickListener
    override fun onClick(view: View?) {
        when(view) {
            binding.btClearSearch -> { viewModel.onClearPressed() }
        }
    }
    //endregion
}