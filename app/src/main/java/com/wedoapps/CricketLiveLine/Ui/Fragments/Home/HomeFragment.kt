package com.wedoapps.CricketLiveLine.Ui.Fragments.Home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.wedoapps.CricketLiveLine.Adapter.HomeCardAdapter
import com.wedoapps.CricketLiveLine.Model.HomeMatch
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.MainActivity
import com.wedoapps.CricketLiveLine.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home), HomeCardAdapter.SetOnClick {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var cardAdapter: HomeCardAdapter
    private lateinit var viewModel: CricketGuruViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        cardAdapter = HomeCardAdapter(this)

        viewModel.getAllMatch()?.observe(requireActivity(), {
            cardAdapter.differ.submitList(it)
        })


        binding.rvHome.apply {
            setHasFixedSize(true)
            adapter = cardAdapter
        }
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvHome)
        binding.rvIndicator.attachToRecyclerView(binding.rvHome)

    }

    override fun onClick(match: HomeMatch) {
        val action = HomeFragmentDirections.actionHomeFragmentToViewPagerActivity(match)
        findNavController().navigate(action)
    }
}