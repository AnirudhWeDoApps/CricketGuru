package com.wedoapps.CricketLiveLine.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.databinding.LayoutMatchBetBinding
import com.wedoapps.CricketLiveLine.databinding.LayoutSessionItemBinding

class SessionBetAdapter(private val listener: SetOn) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (differ.currentList[position].row_type == 2) TYPE_SECTION else TYPE_ITEM
    }

    inner class SectionViewHolder(private val binding: LayoutMatchBetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sessionBet: SessionBet) {
            binding.apply {
                tvplayer.text = sessionBet.playerName
            }
        }
    }

    inner class ItemViewHolder(private val binding: LayoutSessionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(sessionBet: SessionBet) {
            binding.apply {

                tvScore1.text = sessionBet.actualScore.toString()
                tvFirstYes.text = sessionBet.YorN.toString()
                tvTotal.text = "${sessionBet.amount} * ${sessionBet.innings} "

                ivSDelete.setOnClickListener {
                    listener.onDelete(sessionBet, adapterPosition)
                }

                itemView.setOnClickListener {
                    listener.onEdit(sessionBet)
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
            LayoutSessionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

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


    private val differCallback = object : DiffUtil.ItemCallback<SessionBet>() {
        override fun areItemsTheSame(oldItem: SessionBet, newItem: SessionBet): Boolean {
            return oldItem.playerName == newItem.playerName
        }

        override fun areContentsTheSame(oldItem: SessionBet, newItem: SessionBet): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface SetOn {
        fun onDelete(sessionBet: SessionBet, position: Int)
        fun onEdit(sessionBet: SessionBet)
    }

    companion object {
        const val TYPE_SECTION = 0
        const val TYPE_ITEM = 1
    }
}