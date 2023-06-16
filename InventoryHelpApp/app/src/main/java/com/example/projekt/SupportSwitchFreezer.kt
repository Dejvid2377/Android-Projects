package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projekt.databinding.FragmentSupportSwitchFreezerBinding

class SupportSwitchFreezer : Fragment() {
    private var _binding : FragmentSupportSwitchFreezerBinding? = null
    private lateinit var binding : FragmentSupportSwitchFreezerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSupportSwitchFreezerBinding.inflate(layoutInflater,container,false)
        binding = _binding!!
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}