package com.wedoapps.CricketLiveLine.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.PlayerScore
import com.wedoapps.CricketLiveLine.databinding.ScorecardBatsmenItemLayoutBinding

class ScoreCardBattingAdapter : RecyclerView.Adapter<ScoreCardBattingAdapter.BattingViewHolder>() {

    inner class BattingViewHolder(private val binding: ScorecardBatsmenItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(score: PlayerScore) {
            binding.apply {
                tvBatsmanName.text = score.name
                tvBatsmanOut.text = score.otherInfo
                tvFirstRun.text = score.run
                tvFirstBalls.text = score.ball
                tvFirst4s.text = score.fours
                tvFirst6s.text = score.sixes
                tvFirstStrikeRate.text = score.sr
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingViewHolder {
        val binding = ScorecardBatsmenItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BattingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BattingViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private val differCallback = object : DiffUtil.ItemCallback<PlayerScore>() {
        override fun areItemsTheSame(oldItem: PlayerScore, newItem: PlayerScore) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PlayerScore, newItem: PlayerScore) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

}