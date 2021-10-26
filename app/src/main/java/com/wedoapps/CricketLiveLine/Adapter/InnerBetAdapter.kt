package com.wedoapps.CricketLiveLine.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.databinding.InnerMatchBetLayoutBinding

class InnerBetAdapter :
    RecyclerView.Adapter<InnerBetAdapter.InnerBetViewHolder>() {

    inner class InnerBetViewHolder(private val binding: InnerMatchBetLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(matchBet: MatchBet) {
            binding.apply {
                tvTeamName.text = matchBet.team
                tvKl.text = matchBet.type
                tvPlayerName.text = matchBet.playerName
                tvTotal.text = "${matchBet.amount} * ${matchBet.rate}"
                tvTime1.text = matchBet.date

                ivSDelete.setOnClickListener {
//                    listener.onDelete(matchBet)
                }

                itemView.setOnClickListener {
//                    listener.onEdit(matchBet)
                }


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerBetViewHolder {
        val binding =
            InnerMatchBetLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerBetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InnerBetViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<MatchBet>() {
        override fun areItemsTheSame(oldItem: MatchBet, newItem: MatchBet): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: MatchBet, newItem: MatchBet): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface SetOn {
        fun onDelete(matchBet: MatchBet)
        fun onEdit(matchBet: MatchBet)
    }
}