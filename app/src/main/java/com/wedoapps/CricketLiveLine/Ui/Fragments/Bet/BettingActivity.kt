package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.wedoapps.CricketLiveLine.Adapter.ViewPagerAdapter
import com.wedoapps.CricketLiveLine.Db.CricketGuruDatabase
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Repository.CricketGuruRepository
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Match.ActiveMatchFragment
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Party.PartyInfoFragment
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Session.MainSessionFragment
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
            MainSessionFragment().newInstance(id),
            PartyInfoFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )

        binding.viewPagerBetting.adapter = adapter

        binding.toolbar.declare.setOnClickListener {
            Toast.makeText(this, "Declare Result", Toast.LENGTH_SHORT).show()
        }


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

    override fun onResume() {
        super.onResume()

        binding.tabLayoutBetting.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> binding.toolbar.declare.visibility = View.VISIBLE
                    1 -> binding.toolbar.declare.visibility = View.GONE
                    2 -> binding.toolbar.declare.visibility = View.GONE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

}