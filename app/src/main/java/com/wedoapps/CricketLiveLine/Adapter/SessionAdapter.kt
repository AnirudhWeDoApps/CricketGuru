package com.wedoapps.CricketLiveLine.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.Session
import com.wedoapps.CricketLiveLine.databinding.HeaderSessionBinding
import com.wedoapps.CricketLiveLine.databinding.SessionItemLayoutBinding

class SessionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_LIST
    }

    inner class SessionHeaderViewHolder(binding: HeaderSessionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {

        }

    }

    inner class SessionViewHolder(private val binding: SessionItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(session: Session) {
            binding.apply {
                tvOver.text = session.Name
                tvOpen.text = session.Open
                tvMin.text = session.Min
                tvMax.text = session.Max
                tvDone.text = session.Complete
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val binding =
                HeaderSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SessionHeaderViewHolder(binding)
        }
        val binding =
            SessionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is SessionHeaderViewHolder) {
            holder.bind()
        } else if (holder is SessionViewHolder) {
            val currentItem = differ.currentList[position - 1]
            holder.bind(currentItem)
        }
        /* if (currentItem != null) {
             holder.bind(currentItem)
         }*/
    }

    override fun getItemCount(): Int {
        return differ.currentList.size + 1
    }

    private val differCallback = object : DiffUtil.ItemCallback<Session>() {
        override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    companion object {
        const val TYPE_LIST: Int = 1
        const val TYPE_HEADER: Int = 0
    }
}