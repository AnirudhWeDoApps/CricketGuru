package com.wedoapps.CricketLiveLine.Ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.wedoapps.CricketLiveLine.Db.CricketGuruDatabase
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Repository.CricketGuruRepository
import com.wedoapps.CricketLiveLine.Utils.ViewModelProviderFactory
import com.wedoapps.CricketLiveLine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CricketGuruViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = CricketGuruRepository(CricketGuruDatabase(this))
        val viewModelProvider = ViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProvider)[CricketGuruViewModel::class.java]
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
    }
}