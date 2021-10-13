package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Session

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Utils.Constants
import com.wedoapps.CricketLiveLine.databinding.FragmentCheckGraphBinding

class CheckGraphFragment : Fragment(R.layout.fragment_check_graph) {

    private lateinit var binding: FragmentCheckGraphBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckGraphBinding.bind(view)


    }

    fun newInstance(myString: String?): CheckGraphFragment {
        val myFragment = CheckGraphFragment()
        val args = Bundle()
        args.putString(Constants.ID, myString)
        myFragment.arguments = args
        return myFragment
    }

}