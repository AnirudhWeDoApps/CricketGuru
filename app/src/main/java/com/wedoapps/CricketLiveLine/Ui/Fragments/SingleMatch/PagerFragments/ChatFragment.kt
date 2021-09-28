package com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.PagerFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.databinding.FragmentChatBinding

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var binding: FragmentChatBinding
    private lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(view)

        id = arguments?.getString(ID).toString()

    }

    fun newInstance(myString: String?): ChatFragment {
        val myFragment = ChatFragment()
        val args = Bundle()
        args.putString(ID, myString)
        myFragment.arguments = args
        return myFragment
    }

}