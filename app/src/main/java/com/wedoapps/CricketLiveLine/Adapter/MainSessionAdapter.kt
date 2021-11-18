package com.wedoapps.CricketLiveLine.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.SessionBet.MainSession
import com.wedoapps.CricketLiveLine.databinding.LayoutMainSessionBinding

class MainSessionAdapter(private val listener: OnClicks) :
    RecyclerView.Adapter<MainSessionAdapter.MainSessionViewHolder>() {

    inner class MainSessionViewHolder(val binding: LayoutMainSessionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(mainSession: MainSession) {
            binding.apply {
                tvSessionName.text = "${mainSession.sessionName } Overs"
                tvTeamName.text = mainSession.selectedTeamName
                tvTime.text = mainSession.dataAndTime

                ivEditMain.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = differ.currentList[position]
                        if (item != null) {
                            listener.onEdit(item)
                        }
                    }
                }

                ivDeleteMain.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = differ.currentList[position]
                        if (item != null) {
                            listener.onDelete(item)
                        }
                    }
                }

                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = differ.currentList[position]
                        if (item != null) {
                            listener.onClick(item)
                        }
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainSessionViewHolder {
        val binding =
            LayoutMainSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainSessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainSessionViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<MainSession>() {
        override fun areItemsTheSame(oldItem: MainSession, newItem: MainSession): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MainSession, newItem: MainSession): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface OnClicks {
        fun onEdit(mainSession: MainSession)
        fun onDelete(mainSession: MainSession)
        fun onClick(mainSession: MainSession)
    }
}