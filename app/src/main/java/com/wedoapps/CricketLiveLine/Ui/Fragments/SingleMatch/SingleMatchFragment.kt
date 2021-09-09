package com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.wedoapps.CricketLiveLine.Adapter.ViewPagerAdapter
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.PagerFragments.*
import com.wedoapps.CricketLiveLine.databinding.FragmentSingleMatchBinding

class SingleMatchFragment : Fragment(R.layout.fragment_single_match) {

    private lateinit var binding: FragmentSingleMatchBinding
    private val args by navArgs<SingleMatchFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSingleMatchBinding.bind(view)

        val id = args.data.id

        val fragmentList = arrayListOf(
            LiveLineFragment(),
            InfoFragment(id),
            ChatFragment(),
            CommentaryFragment(),
            ScorecardFragment(id)
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.live_line)
                1 -> tab.text = getString(R.string.info)
                2 -> tab.text = getString(R.string.chat)
                3 -> tab.text = getString(R.string.commentary)
                4 -> tab.text = getString(R.string.scorecard)
            }
        }.attach()
    }
}