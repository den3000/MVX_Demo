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
    IViewToController,
    IControllerToView,
    IControllerToModel,
    IModelToController
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
        progress(show = true)
        scope.launch {
            resetModel()
            withContext(Dispatchers.Main) {
                val dataset = modelDataset()
                results(display = dataset.isNotEmpty())
                adapter = ItemsAdapter(dataset)
                binding.rvItems.layoutManager = LinearLayoutManager(this@ViewMvcActivity)
                binding.rvItems.adapter = adapter
                progress(show = false)
            }
        }
    }

    override fun filterList(cs: CharSequence?) {
        textChangedJob?.cancel()
        progress(show = true)
        textChangedJob = scope.launch {
            if (cs.isNullOrEmpty()) {
                resetModel()
            } else {
                filterModel(cs.toString())
            }

            withContext(Dispatchers.Main) {
                val dataset = modelDataset()
                results(display = dataset.isNotEmpty())
                adapter?.dataSet = dataset
                progress(show = false)
            }
        }
    }

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
    //endregion

    //region ControllerToView
    override suspend fun resetModel() { model.all() }

    override suspend fun filterModel(text: String) { model.filter(text) }
    //endregion

    //region ModelToController
    override fun modelDataset(): List<String> = model.dataset
    //endregion
}

private interface IViewToController:
    TextWatcher,
    View.OnClickListener

private interface IControllerToView {
    fun clearSearchText()
    fun initList()
    fun filterList(cs: CharSequence?)
    fun progress(show: Boolean)
    fun results(display: Boolean)
}

private interface IControllerToModel {
    suspend fun resetModel()
    suspend fun filterModel(text: String)
}

private interface IModelToController {
    fun modelDataset(): List<String>
}