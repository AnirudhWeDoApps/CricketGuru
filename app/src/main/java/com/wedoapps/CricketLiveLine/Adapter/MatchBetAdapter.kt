package com.wedoapps.CricketLiveLine.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.databinding.InnerMatchBetLayoutBinding
import com.wedoapps.CricketLiveLine.databinding.LayoutMatchBetBinding

class MatchBetAdapter(val listener: SetOn) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (differ.currentList[position].row_type == 2) TYPE_SECTION else TYPE_ITEM
    }

    inner class SectionViewHolder(private val binding: LayoutMatchBetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(matchBet: MatchBet) {
            binding.apply {
                tvplayer.text = matchBet.playerName
            }
        }
    }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_SECTION) {
            val binding =
                LayoutMatchBetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SectionViewHolder(binding)
        }

        val binding =
            InnerMatchBetLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        /*     if (TYPE_SECTION == getItemViewType(position)) {
                  val sectionViewHolder = holder as SectionViewHolder
                  sectionViewHolder.bind(currentItem)
                  return
              }*/

        if (currentItem.row_type == 1) {
            val itemViewHolder = holder as ItemViewHolder
            itemViewHolder.bind(currentItem)
        } else {
            val sectionViewHolder = holder as SectionViewHolder
            sectionViewHolder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private val differCallback = object : DiffUtil.ItemCallback<MatchBet>() {
        override fun areItemsTheSame(oldItem: MatchBet, newItem: MatchBet): Boolean {
            return oldItem.playerName == newItem.playerName
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

    companion object {
        const val TYPE_SECTION = 0
        const val TYPE_ITEM = 1
    }


}