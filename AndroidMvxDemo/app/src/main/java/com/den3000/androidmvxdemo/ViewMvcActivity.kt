package com.den3000.androidmvxdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.den3000.androidmvxdemo.databinding.ActivityViewMvcBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewMvcActivity : AppCompatActivity(),
    ViewToController,
    ControllerToView
{
    private lateinit var binding: ActivityViewMvcBinding

    private var model = ItemsModel()
    private var scope = CoroutineScope(context = Dispatchers.IO)
    private var textChangedJob: Job? = null
    private var adapter: ItemsAdapter? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMvcBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvTitle.text = "View MVC"
        binding.btClearSearch.setOnClickListener(this)
        binding.etSearchString.addTextChangedListener(this)

        initList()
    }

    //region ViewToController
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {
        filterList(cs)
    }

    override fun afterTextChanged(p0: Editable?) { }

    override fun onClick(view: View?) {
        when(view) {
            binding.btClearSearch -> clearSearchText()
        }
    }
    //endregion

    //region ControllerToView
    override fun clearSearchText() {
        binding.etSearchString.text = null
    }

    override fun initList() {
        scope.launch {
            val dataset = model.all()
            withContext(Dispatchers.Main) {
                adapter = ItemsAdapter(dataset)
                binding.rvItems.layoutManager = LinearLayoutManager(this@ViewMvcActivity)
                binding.rvItems.adapter = adapter
            }
        }
    }

    override fun filterList(cs: CharSequence?) {
        textChangedJob?.cancel()
        textChangedJob = scope.launch {
            val dataset = if (cs.isNullOrEmpty()) {
                model.all()
            } else {
                model.filter(cs.toString())
            }

            withContext(Dispatchers.Main) {
                adapter?.dataSet = dataset
            }
        }
    }
    //endregion
}

private interface ViewToController:
    TextWatcher,
    View.OnClickListener

private interface ControllerToView {
    fun clearSearchText()
    fun initList()
    fun filterList(cs: CharSequence?)
}