package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projekt.databinding.FragmentSupportSwitchKitchenBinding

class SupportSwitchKitchen : Fragment() {
    private var _binding : FragmentSupportSwitchKitchenBinding? = null
    private lateinit var binding : FragmentSupportSwitchKitchenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSupportSwitchKitchenBinding.inflate(layoutInflater,container,false)
        binding = _binding!!
        return binding.root
    }

    private fun setKitchenSwitchButtonClickListener(listener: View.OnClickListener) {
        _binding?.kitchenSwitchButton?.setOnClickListener(listener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setButtonClickListener(listener: () -> Unit) {
        binding.kitchenSwitchButton.setOnClickListener { listener.invoke() }
    }
}