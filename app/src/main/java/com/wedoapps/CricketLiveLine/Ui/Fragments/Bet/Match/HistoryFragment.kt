package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Match

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private lateinit var binding: FragmentHistoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)
    }

}