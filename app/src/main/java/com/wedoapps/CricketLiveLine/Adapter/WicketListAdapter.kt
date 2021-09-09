package com.wedoapps.CricketLiveLine.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.WicketFall
import com.wedoapps.CricketLiveLine.databinding.WicketItemLayoutBinding

class WicketListAdapter : RecyclerView.Adapter<WicketListAdapter.WicketListViewHolder>() {

    inner class WicketListViewHolder(private val binding: WicketItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(wicket: WicketFall) {
            binding.apply {
                tvOutName.text = wicket.name
                tvOver.text = wicket.over
                tvScore.text = wicket.score
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WicketListViewHolder {
        val binding =
            WicketItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WicketListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WicketListViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<WicketFall>() {
        override fun areItemsTheSame(oldItem: WicketFall, newItem: WicketFall) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: WicketFall, newItem: WicketFall) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

}