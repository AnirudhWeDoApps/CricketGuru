package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Session

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.wedoapps.CricketLiveLine.Adapter.ViewPagerAdapter
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Utils.Constants
import com.wedoapps.CricketLiveLine.databinding.FragmentCurrentSessionBinding

class CurrentSessionFragment : Fragment(R.layout.fragment_current_session) {

    private lateinit var binding: FragmentCurrentSessionBinding
    lateinit var viewModel: CricketGuruViewModel
    private lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrentSessionBinding.bind(view)

        id = arguments?.getString(Constants.ID).toString()


        val fragmentList = arrayListOf(
            SessionEntryFragment().newInstance(id),
            CheckGraphFragment().newInstance(id)
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            parentFragmentManager,
            lifecycle
        )

        binding.viewPagerSession.adapter = adapter

        TabLayoutMediator(binding.tabLayoutSession, binding.viewPagerSession) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.SessionEntry)
                1 -> tab.text = getString(R.string.CheckGraph)
            }
        }.attach()

    }

    fun newInstance(myString: String?): CurrentSessionFragment {
        val myFragment = CurrentSessionFragment()
        val args = Bundle()
        args.putString(Constants.ID, myString)
        myFragment.arguments = args
        return myFragment
    }

}

