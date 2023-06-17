package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the click listener for the freezerSwitchButton
        setFreezerSwitchButtonClickListener {
            // Perform your desired action here
            // For example, navigate to another fragment
            findNavController().navigate(R.id.action_menu_to_freezerResources)
        }
    }

    private fun setFreezerSwitchButtonClickListener(listener: View.OnClickListener) {
        _binding?.freezerSwitchButton?.setOnClickListener(listener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setButtonClickListener(listener: () -> Unit) {
        binding.freezerSwitchButton.setOnClickListener { listener.invoke() }
    }

}