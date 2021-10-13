package com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.wedoapps.CricketLiveLine.Adapter.ViewPagerAdapter
import com.wedoapps.CricketLiveLine.Db.CricketGuruDatabase
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Repository.CricketGuruRepository
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.PagerFragments.*
import com.wedoapps.CricketLiveLine.Utils.ViewModelProviderFactory
import com.wedoapps.CricketLiveLine.databinding.ActivityViewPagerBinding

class ViewPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPagerBinding
    private val args by navArgs<ViewPagerActivityArgs>()
    lateinit var viewModel: CricketGuruViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar.toolbarTop
        setSupportActionBar(toolbar)

        val id = args.data.id

        val fragmentList = arrayListOf(
            LiveLineFragment().newInstance(id),
            ScorecardFragment().newInstance(id),
            BetFragment().newInstance(id),
            SessionFragment().newInstance(id),
            InfoFragment().newInstance(id)
            /*ChatFragment().newInstance(id),
            CommentaryFragment().newInstance(id),*/
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.live_line)
                1 -> tab.text = getString(R.string.scorecard)
                2 -> tab.text = getString(R.string.bet)
                3 -> tab.text = getString(R.string.session)
                4 -> tab.text = getString(R.string.info)
//                5 -> tab.text = getString(R.string.chat)
//                6 -> tab.text = getString(R.string.commentary)
            }
        }.attach()

        val repository = CricketGuruRepository(CricketGuruDatabase(this))
        val viewModelProvider = ViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProvider)[CricketGuruViewModel::class.java]

    }
}