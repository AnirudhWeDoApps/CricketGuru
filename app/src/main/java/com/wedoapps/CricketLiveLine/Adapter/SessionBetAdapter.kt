package com.wedoapps.CricketLiveLine.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.databinding.LayoutSessionItemBinding

class SessionBetAdapter(private val listener: SetOn) :
    RecyclerView.Adapter<SessionBetAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: LayoutSessionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(sessionBet: SessionBet) {
            binding.apply {

                tvScore1.text = sessionBet.actualScore.toString()
                tvFirstYes.text = sessionBet.YorN.toString()
                tvTotal.text = "${sessionBet.amount} * ${sessionBet.rate} "

                ivSDelete.setOnClickListener {
                    listener.onDelete(sessionBet, adapterPosition)
                }

                itemView.setOnClickListener {
                    listener.onEdit(sessionBet)
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SessionBetAdapter.ItemViewHolder {
        val binding =
            LayoutSessionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionBetAdapter.ItemViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private val differCallback = object : DiffUtil.ItemCallback<SessionBet>() {
        override fun areItemsTheSame(oldItem: SessionBet, newItem: SessionBet): Boolean {
            return oldItem.id == newItem.id
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

}