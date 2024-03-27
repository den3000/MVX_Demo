package com.den3000.androidmvxdemo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.den3000.androidmvxdemo.databinding.ViewHolderItemBinding

class ItemsAdapter(dataSet: List<String>) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    var dataSet: List<String> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    init { this.dataSet = dataSet }

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