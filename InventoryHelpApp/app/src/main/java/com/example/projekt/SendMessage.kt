package com.example.projekt

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projekt.databinding.FragmentSendMessageBinding

class SendMessage : Fragment() {
    private var _binding : FragmentSendMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSendMessageBinding.inflate(inflater, container, false)
        val view = binding.root

        // Send email here
        sendEmail()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendEmail() {
        // Add your email sending logic here
        // You can use the JavaMail API or other email libraries to send the email
        // Make sure to handle any necessary permissions and configurations for sending emails

        // Example code to send an email using Intent
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Subject")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email Body")

        val chooserIntent = Intent.createChooser(emailIntent, "Send Email")
        startActivity(chooserIntent)
    }
}
