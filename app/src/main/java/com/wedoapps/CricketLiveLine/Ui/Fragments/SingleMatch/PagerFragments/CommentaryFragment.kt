package com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.PagerFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.databinding.FragmentCommentaryBinding

class CommentaryFragment : Fragment(R.layout.fragment_commentary) {

    private lateinit var binding: FragmentCommentaryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCommentaryBinding.bind(view)
    }

}