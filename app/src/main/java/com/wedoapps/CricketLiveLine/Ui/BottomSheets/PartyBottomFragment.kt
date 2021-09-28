package com.wedoapps.CricketLiveLine.Ui.BottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.databinding.FragmentBottomPartyBinding

class PartyBottomFragment : BottomSheetDialogFragment() {

    private var isSelected = false
    private lateinit var binding: FragmentBottomPartyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_party, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBottomPartyBinding.bind(view)

        binding.ivCancel.setOnClickListener {
            dismiss()
        }

        // ODI/ T-20 Commission
        /* binding.apply {
             tvOdiGive.setOnClickListener {
                 isSelected = true
                 tvOdiTake.background = null
                 tvOdiTake.setTextColor(Color.BLACK)
                 tvOdiGive.setTextColor(Color.WHITE)
                 tvOdiGive.background =
                     ContextCompat.getDrawable(requireContext(), R.drawable.reverse_select_bg)
             }

             binding.apply {
                 tvOdiTake.setOnClickListener {
                     isSelected = false
                     tvOdiGive.background = null
                     tvOdiGive.setTextColor(Color.BLACK)
                     tvOdiTake.setTextColor(Color.WHITE)
                     tvOdiTake.background =
                         ContextCompat.getDrawable(requireContext(), R.drawable.select_bg)
                 }
             }

             // Test Commission
             tvTestGive.setOnClickListener {
                 isSelected = true
                 tvTestTake.background = null
                 tvTestTake.setTextColor(Color.BLACK)
                 tvTestGive.setTextColor(Color.WHITE)
                 tvTestGive.background =
                     ContextCompat.getDrawable(requireContext(), R.drawable.reverse_select_bg)
             }

             binding.apply {
                 tvTestTake.setOnClickListener {
                     isSelected = false
                     tvTestGive.background = null
                     tvTestGive.setTextColor(Color.BLACK)
                     tvTestTake.setTextColor(Color.WHITE)
                     tvTestTake.background =
                         ContextCompat.getDrawable(requireContext(), R.drawable.select_bg)
                 }
             }

             // Session Commission
             tvSessionGive.setOnClickListener {
                 isSelected = true
                 tvSessionTake.background = null
                 tvSessionTake.setTextColor(Color.BLACK)
                 tvSessionGive.setTextColor(Color.WHITE)
                 tvSessionGive.background =
                     ContextCompat.getDrawable(requireContext(), R.drawable.reverse_select_bg)
             }

             binding.apply {
                 tvSessionTake.setOnClickListener {
                     isSelected = false
                     tvSessionGive.background = null
                     tvSessionGive.setTextColor(Color.BLACK)
                     tvSessionTake.setTextColor(Color.WHITE)
                     tvSessionTake.background =
                         ContextCompat.getDrawable(requireContext(), R.drawable.select_bg)
                 }
             }

         }
         binding.btnAdd.setOnClickListener {
             if (isSelected) {
                 Toast.makeText(requireContext(), "Give", Toast.LENGTH_SHORT).show()
             } else {
                 Toast.makeText(requireContext(), "Take", Toast.LENGTH_SHORT).show()
             }
         }*/
    }
}