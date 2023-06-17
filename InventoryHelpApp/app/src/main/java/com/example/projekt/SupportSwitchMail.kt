package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projekt.databinding.FragmentSupportSwitchMailBinding

class SupportSwitchMail : Fragment() {
    private var _binding : FragmentSupportSwitchMailBinding? = null
    private lateinit var binding: FragmentSupportSwitchMailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSupportSwitchMailBinding.inflate(layoutInflater,container,false)
        binding = _binding!!
        return binding.root
    }

    private fun setFreezerSwitchButtonClickListener(listener: View.OnClickListener) {
        _binding?.mailSwitchButton?.setOnClickListener(listener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setButtonClickListener(listener: () -> Unit) {
        binding.mailSwitchButton.setOnClickListener { listener.invoke() }
    }

}