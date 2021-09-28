package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Party

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.databinding.FragmentPartyInfoBinding

class PartyInfoFragment : Fragment(R.layout.fragment_party_info) {

    private lateinit var binding: FragmentPartyInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPartyInfoBinding.bind(view)


        /*  binding.fabAddMatch.setOnClickListener {
              val partyInfo = PartyBottomFragment()
              partyInfo.show(childFragmentManager, partyInfo.tag)
          }*/
    }

}