package com.den3000.androidmvxdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.den3000.androidmvxdemo.databinding.ActivityViewBinding

class ViewMvpActivity : AppCompatActivity(),
    View.OnClickListener,
    TextWatcher,
    ItemsPresenter.IView
{
    private lateinit var binding: ActivityViewBinding

    private var adapter: ItemsAdapter? = null
    private var presenter: ItemsPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvTitle.text = "View MVP"
        binding.btClearSearch.setOnClickListener(this)
        binding.etSearchString.addTextChangedListener(this)

        adapter = ItemsAdapter(emptyList())
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = adapter

        presenter = ItemsPresenter(this)
    }

    //region TextWatcher
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {
        presenter?.onSearchTextChanged(cs)
    }

    override fun afterTextChanged(p0: Editable?) { }
    //endregion

    //region View.OnClickListener
    override fun onClick(view: View?) {
        when(view) {
            binding.btClearSearch -> { presenter?.onClearPressed() }
        }
    }
    //endregion

    //region ItemsPresenter.IView
    override fun clearSearchText() { binding.etSearchString.text = null }

    override fun progress(show: Boolean) {
        binding.piSearch.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun results(display: Boolean) {
        if (display) {
            binding.tvNoResults.visibility = View.GONE
            binding.rvItems.visibility = View.VISIBLE
        } else {
            binding.tvNoResults.visibility = View.VISIBLE
            binding.rvItems.visibility = View.GONE
        }
    }

    override fun update(list: List<String>) { adapter?.dataSet = list }
    //endregion
}