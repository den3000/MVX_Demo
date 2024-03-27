package com.den3000.androidmvxdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.den3000.androidmvxdemo.databinding.ViewHolderItemBinding

class ItemsAdapter(private val dataSet: Array<String>) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ViewHolderItemBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.tvContent.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size

    class ViewHolder(val binding: ViewHolderItemBinding) : RecyclerView.ViewHolder(binding.root)
}