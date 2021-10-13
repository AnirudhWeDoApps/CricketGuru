package com.wedoapps.CricketLiveLine.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.databinding.LayoutSessionItemBinding

class SessionBetAdapter(private val listener: OnSet) :
    RecyclerView.Adapter<SessionBetAdapter.SessionBetViewHolder>() {

    inner class SessionBetViewHolder(private val binding: LayoutSessionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(sessionBet: SessionBet) {
            binding.apply {
                val fandp = sessionBet.FandP
                val actualScore = sessionBet.actualScore!!

                tvScore1.text = sessionBet.actualScore.toString()
                tvFirstYes.text = sessionBet.YorN.toString()
                tvTotal.text = "${sessionBet.amount} * ${sessionBet.innings} "
              /*  tvPreScore.text = sessionBet.FandP.toString()
                tvScore.text = sessionBet.actualScore.toString()
                tvAmt.text = sessionBet.amount.toString()*/

               /* if (fandp!! > actualScore) {
                    cardResult.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            android.R.color.holo_green_dark
                        )
                    )
                    tvResult.text = "Win: ${sessionBet.amount}"
                } else {
                    cardResult.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            android.R.color.holo_red_dark
                        )
                    )
                    tvResult.text = "Loss: -${sessionBet.amount}"
                }*/

                ivSDelete.setOnClickListener {
                    listener.onDeleteSession(sessionBet)
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
    ): SessionBetAdapter.SessionBetViewHolder {
        val binding =
            LayoutSessionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SessionBetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionBetAdapter.SessionBetViewHolder, position: Int) {
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


    interface OnSet {
        fun onDeleteSession(sessionBet: SessionBet)
        fun onEdit(sessionBet: SessionBet)
    }

}