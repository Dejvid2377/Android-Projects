package com.example.projekt

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.projekt.databinding.FragmentStartScreenBinding


class StartScreen : Fragment() {
    private var _binding: FragmentStartScreenBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartScreenBinding.inflate(layoutInflater, container,false)

        mainViewModel.sheetName.observe(viewLifecycleOwner) { sheetName ->
            binding.raportName.setText(sheetName)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.raportName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check the length of the text and enable/disable the button accordingly
                val isTextNotEmpty = (s?.length ?: 0) > 0
                binding.acceptButton.isEnabled = isTextNotEmpty
            }

            override fun afterTextChanged(s: Editable?) { }


        })

        binding.acceptButton.setOnClickListener {
            mainViewModel.saveSheetName(binding.raportName.text.toString())
            findNavController().navigate(R.id.action_startScreen_to_menu)
        }

        binding.circle.setOnClickListener {
            binding.circle.animate().apply {
                duration = 1000
                rotationBy(360f)
                start()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}