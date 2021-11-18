package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Session

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.wedoapps.CricketLiveLine.Adapter.ViewPagerAdapter
import com.wedoapps.CricketLiveLine.Db.CricketGuruDatabase
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Repository.CricketGuruRepository
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.Utils.Constants.SESSION_ID
import com.wedoapps.CricketLiveLine.Utils.Constants.SESSION_NAME
import com.wedoapps.CricketLiveLine.Utils.ViewModelProviderFactory
import com.wedoapps.CricketLiveLine.databinding.FragmentCurrentSessionBinding

class SessionEntryActivity : AppCompatActivity() {

    private lateinit var binding: FragmentCurrentSessionBinding
    lateinit var viewModel: CricketGuruViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCurrentSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionName = intent.getStringExtra(SESSION_NAME)!!

        val toolbar = binding.toolbar.toolbarTop
        binding.toolbar.tvTitle.text = "$sessionName Overs"
        setSupportActionBar(toolbar)


        binding.toolbar.declare.setOnClickListener {
            Toast.makeText(this, "Declare Result", Toast.LENGTH_SHORT).show()
        }

        val id = intent.getStringExtra(ID)!!
        val sessionID = intent.getIntExtra(SESSION_ID, 0)

        val fragmentList = arrayListOf(
            SessionEntryFragment().newInstance(id, sessionID.toString()),
            CheckGraphFragment().newInstance(id, sessionID.toString())
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )

        binding.viewPagerSession.adapter = adapter

        TabLayoutMediator(binding.tabLayoutSession, binding.viewPagerSession) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.SessionEntry)
                1 -> tab.text = getString(R.string.CheckGraph)
            }
        }.attach()

        val repository = CricketGuruRepository(CricketGuruDatabase(this))
        val viewModelProvider = ViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProvider)[CricketGuruViewModel::class.java]

    }
}