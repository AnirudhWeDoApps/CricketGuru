package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.wedoapps.CricketLiveLine.Adapter.ViewPagerAdapter
import com.wedoapps.CricketLiveLine.Db.CricketGuruDatabase
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Repository.CricketGuruRepository
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Match.ActiveMatchFragment
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Party.PartyInfoFragment
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Session.CurrentSessionFragment
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.Utils.ViewModelProviderFactory
import com.wedoapps.CricketLiveLine.databinding.ActivityBettingBinding

class BettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBettingBinding
    lateinit var viewModel: CricketGuruViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar.toolbarTop
        setSupportActionBar(toolbar)

        val intent = intent
        val id = intent.getStringExtra(ID)!!

        val fragmentList = arrayListOf(
            ActiveMatchFragment().newInstance(id),
            CurrentSessionFragment().newInstance(id),
            PartyInfoFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )

        binding.viewPagerBetting.adapter = adapter

        TabLayoutMediator(binding.tabLayoutBetting, binding.viewPagerBetting) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.ActiveMatch)
                1 -> tab.text = getString(R.string.CurrentSession)
                2 -> tab.text = getString(R.string.party_info)
            }
        }.attach()


        val repository = CricketGuruRepository(CricketGuruDatabase(this))
        val viewModelProvider = ViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProvider)[CricketGuruViewModel::class.java]

    }
}