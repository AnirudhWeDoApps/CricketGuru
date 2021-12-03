package com.wedoapps.CricketLiveLine.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchData
import com.wedoapps.CricketLiveLine.databinding.LayoutMatchBetBinding

class MatchDataAdapter(private val listener: SetOn) :
    RecyclerView.Adapter<MatchDataAdapter.SectionViewHolder>() {

    inner class SectionViewHolder(private val binding: LayoutMatchBetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(matchData: MatchData) {
            binding.apply {
                tvplayer.text = matchData.playerName
                val matchBetAdapter = MatchBetAdapter(object : MatchBetAdapter.SetOn {
                    override fun onDelete(matchBet: MatchBet, position: Int) {
                        listener.onDeleteMatchBet(matchBet)
                    }

                    override fun onEdit(matchBet: MatchBet) {
                        listener.onEditMatchBet(matchBet)
                    }
                })
                matchBetAdapter.differ.submitList(matchData.matchBet)
                rvInnerLa.apply {
                    setHasFixedSize(true)
                    adapter = matchBetAdapter
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchDataAdapter.SectionViewHolder {
        val binding =
            LayoutMatchBetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchDataAdapter.SectionViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private val differCallback = object : DiffUtil.ItemCallback<MatchData>() {
        override fun areItemsTheSame(oldItem: MatchData, newItem: MatchData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MatchData, newItem: MatchData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface SetOn {
        fun onDeleteMatchBet(matchBet: MatchBet)
        fun onEditMatchBet(matchBet: MatchBet)
    }

}