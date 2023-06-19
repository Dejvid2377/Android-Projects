package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.projekt.databinding.FragmentMenuBinding

class Menu : Fragment() {
    private var _binding : FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create instances of the sub-fragments
        val kitchenFragment = SupportSwitchKitchen()
        val freezerFragment = SupportSwitchFreezer()
        val mailFragment = SupportSwitchMail()

        // Load the sub-fragments into the FragmentContainerViews
        childFragmentManager.beginTransaction()
            .replace(R.id.kitchenSwitchFragment, kitchenFragment)
            .replace(R.id.freezerSwitchFragment, freezerFragment)
            .replace(R.id.mailSwitchFragment, mailFragment)
            .commit()

        // Wait until the view is created before setting the click listener
        view.post {
            freezerFragment.setButtonClickListener {
                findNavController().navigate(R.id.action_menu_to_freezerResources)
            }

            mailFragment.setButtonClickListener {
                findNavController().navigate(R.id.action_menu_to_sendMessage)
            }

            kitchenFragment.setButtonClickListener {
                findNavController().navigate(R.id.action_menu_to_kitchenResources)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}