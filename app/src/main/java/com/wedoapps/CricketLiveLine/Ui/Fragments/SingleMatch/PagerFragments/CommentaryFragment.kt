package com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.PagerFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Utils.Constants
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.databinding.FragmentCommentaryBinding

class CommentaryFragment : Fragment(R.layout.fragment_commentary) {

    private lateinit var binding: FragmentCommentaryBinding
    private lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCommentaryBinding.bind(view)

        id = arguments?.getString(ID).toString()
    }


    fun newInstance(myString: String?): CommentaryFragment {
        val myFragment = CommentaryFragment()
        val args = Bundle()
        args.putString(Constants.ID, myString)
        myFragment.arguments = args
        return myFragment
    }

}