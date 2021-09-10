package com.wedoapps.CricketLiveLine.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.Bowlers
import com.wedoapps.CricketLiveLine.databinding.BowlingItemLayoutBinding

class BowlerListAdapter : RecyclerView.Adapter<BowlerListAdapter.BowlerListViewHolder>() {

    inner class BowlerListViewHolder(private val binding: BowlingItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(bowler: Bowlers) {
            binding.apply {
                tvBowlerName.text = bowler.Name
                tvOver.text = bowler.Over
                tvMaiden.text = bowler.Maiden
                tvRuns.text = bowler.Run
                tvWicket.text = bowler.Wicket
                tvEr.text = bowler.ER
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BowlerListViewHolder {
        val binding =
            BowlingItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BowlerListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BowlerListViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<Bowlers>() {
        override fun areItemsTheSame(oldItem: Bowlers, newItem: Bowlers) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Bowlers, newItem: Bowlers) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

}