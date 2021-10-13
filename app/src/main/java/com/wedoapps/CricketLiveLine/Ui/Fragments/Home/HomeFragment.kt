package com.wedoapps.CricketLiveLine.Ui.Fragments.Home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.wedoapps.CricketLiveLine.Adapter.HomeCardAdapter
import com.wedoapps.CricketLiveLine.Model.HomeMatch
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.MainActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.FragmentHomeBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


class HomeFragment : Fragment(R.layout.fragment_home), HomeCardAdapter.SetOnClick {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var cardAdapter: HomeCardAdapter
    private lateinit var viewModel: CricketGuruViewModel
    private var team1 = ArrayList<String>()
    private var team2 = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setImage(
            Environment.getExternalStorageDirectory()
                .toString() + "/Android/data/" + requireActivity().applicationContext.packageName + "/Files/Banner.jpeg"
        )

        viewModel = (activity as MainActivity).viewModel

        cardAdapter = HomeCardAdapter(this)

        viewModel.getAllMatch().observe(requireActivity(), {
            cardAdapter.differ.submitList(it)
            it.forEach { data ->
                team1.add(data.Team1.toString())
                team2.add(data.Team2.toString())
            }
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
//        scheduleJob()
    }

    private fun setImage(imgPath: String) {
        try {
            val f = File(imgPath)
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            binding.ivBannerAd.setImageBitmap(b)
            Log.d(TAG, "setImage: DOne")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.d(TAG, "setImage: $e")
        }
    }


    override fun onStart() {
        super.onStart()
//        startWorker()
        setImage(
            Environment.getExternalStorageDirectory()
                .toString() + "/Android/data/" + requireActivity().applicationContext.packageName + "/Files/Banner.jpeg"
        )
    }


    override fun onResume() {
        super.onResume()
        setImage(
            Environment.getExternalStorageDirectory()
                .toString() + "/Android/data/" + requireActivity().applicationContext.packageName + "/Files/Banner.jpeg"
        )
    }


}