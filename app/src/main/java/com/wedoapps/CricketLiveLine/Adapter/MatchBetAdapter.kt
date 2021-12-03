package com.wedoapps.CricketLiveLine.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.databinding.InnerMatchBetLayoutBinding

class MatchBetAdapter(val listener: SetOn) :
    RecyclerView.Adapter<MatchBetAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: InnerMatchBetLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(matchBet: MatchBet) {
            binding.apply {

                tvTeamName.text = matchBet.team
                tvKl.text = matchBet.type
                tvTotal.text = "${matchBet.amount} * ${matchBet.rate}"
                tvTime1.text = matchBet.date

                ivSDelete.setOnClickListener {
                    listener.onDelete(matchBet, adapterPosition)
                }

                itemView.setOnClickListener {
                    listener.onEdit(matchBet)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchBetAdapter.ItemViewHolder {
        val binding =
            InnerMatchBetLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchBetAdapter.ItemViewHolder, position: Int) {
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
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MatchBet, newItem: MatchBet): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface SetOn {
        fun onDelete(matchBet: MatchBet, position: Int)
        fun onEdit(matchBet: MatchBet)
    }

}