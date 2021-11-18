package com.wedoapps.CricketLiveLine.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBookShowModel
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.LayoutCheckGraphBinding
import java.text.DecimalFormat

class CheckGraphAdapter(val context: Context) :
    RecyclerView.Adapter<CheckGraphAdapter.CheckGraphViewHolder>() {

    inner class CheckGraphViewHolder(val binding: LayoutCheckGraphBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NewApi")
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(model: SessionBookShowModel) {
            binding.apply {
                val indianCurrencyFormat = DecimalFormat("##,##,###")

                if (model.amount != null) {
                    val amount: String = if (model.amount!! >= 0) {
                        "" + "<font color=" + context.resources.getColor(
                            android.R.color.holo_green_dark,
                            context.theme
                        ) + ">" + indianCurrencyFormat.format(model.amount) + "</font>"
                    } else {
                        "" + "<font color=" + context.resources.getColor(
                            android.R.color.holo_red_dark,
                            context.theme
                        ) + ">" + indianCurrencyFormat.format(model.amount) + "</font>"
                    }
                    binding.tvTotal.text =
                        HtmlCompat.fromHtml(amount, HtmlCompat.FROM_HTML_MODE_LEGACY)
                }
                if (model.isLastRecord!!) {
                    binding.cardBg.setBackgroundColor(
                        context.resources.getColor(
                            R.color.dark_blue,
                            context.theme
                        )
                    )
                    if (model.amount != null) {
                        val amount: String = if (model.amount!! >= 0) {
                            "" + "<b> <font color=" + context.resources.getColor(
                                android.R.color.holo_green_dark,
                                context.theme
                            ) + ">" + indianCurrencyFormat.format(model.amount) + "</font> </b>"
                        } else {
                            "" + "<b> <font color=" + context.resources.getColor(
                                android.R.color.holo_red_dark,
                                context.theme
                            ) + ">" + indianCurrencyFormat.format(model.amount) + "</font> </b>"
                        }
                        binding.tvTotal.text =
                            HtmlCompat.fromHtml(amount, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    }
                    /*binding.tvTotal.textSize =
                        context.resources.getDimension(R.dimen.txt_size_5sp)
                    binding.tvRun.textSize =
                        context.resources.getDimension(R.dimen.txt_size_5sp)*/
                    binding.tvRun.setTextColor(
                        context.resources.getColor(
                            R.color.white,
                            context.theme
                        )
                    )
                } else {
                    Log.d(TAG, "isLastRecord value====>" + model.isLastRecord)
                    //                binding.cardBg.setBackgroundColor(context.getResources().getColor(R.color.colorTransparent,context.getTheme()));
                    val drawable = ContextCompat.getDrawable(context, R.color.white)
                    binding.cardBg.background = drawable
                    binding.tvRun.setTextColor(
                        context.resources.getColor(
                            R.color.black,
                            context.theme
                        )
                    )
                }
                binding.tvRun.text = String.format("Run = %s", model.run)


              /*  tvRun.text = model.run.toString()
                tvTotal.text = model.amount.toString()*/
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckGraphViewHolder {
        val binding =
            LayoutCheckGraphBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CheckGraphViewHolder(binding)
    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: CheckGraphViewHolder, position: Int) {

        val sessionBookShowModel = differ.currentList[position]
        if (sessionBookShowModel != null) {
            holder.bind(sessionBookShowModel)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<SessionBookShowModel>() {
        override fun areItemsTheSame(
            oldItem: SessionBookShowModel,
            newItem: SessionBookShowModel
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: SessionBookShowModel,
            newItem: SessionBookShowModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}