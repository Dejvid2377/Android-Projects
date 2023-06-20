package com.example.projekt

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import com.example.projekt.databinding.FragmentSendMessageBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SendMessage : Fragment() {
    private var _binding : FragmentSendMessageBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSendMessageBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.sendMail.setOnClickListener {
            attach()
        }

        binding.viewFirstFile.setOnClickListener {
            view("${mainViewModel.sheetName.value}_1.xlsx")
        }

        binding.viewSecondFile.setOnClickListener {
            view("${mainViewModel.sheetName.value}_2.xlsx")
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @SuppressLint("SimpleDateFormat")
    private fun attach() {
        val fileNames = arrayOf("${mainViewModel.sheetName.value}_1.xlsx", "${mainViewModel.sheetName.value}_2.xlsx")
        val fileUris = ArrayList<Uri>()
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = dateFormat.format(currentDate)

        for (fileName in fileNames) {
            val file = File(requireContext().filesDir, fileName)
            val uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.project.file-provider",
                file
            )
            fileUris.add(uri)
        }

        val emailIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
        emailIntent.type = "text/plain"
        emailIntent.setPackage("com.google.android.gm")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("przypadkowy.adres@gmail.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Raport z dnia $formattedDate")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Przykładowa treść wewnątrz maila")
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, fileUris)

        startActivity(emailIntent)
    }

    private fun view (fileName : String) {
        val file = File(requireContext().filesDir, fileName)
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "com.example.project.file-provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/vnd.ms-excel")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No application found to view the file", Toast.LENGTH_SHORT).show()
        }
    }

}
