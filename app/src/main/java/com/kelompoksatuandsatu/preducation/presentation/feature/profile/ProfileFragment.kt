package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kelompoksatuandsatu.preducation.databinding.FragmentProfileBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.changepassword.ChangePasswordActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.editprofile.EditProfileActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.historypayment.TransactionActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity

class ProfileFragment : Fragment() {

    private val IMAGE_PICK_REQUEST_CODE = 123

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.ivUserPhoto.setOnClickListener {
            startImageUpload()
        }

        binding.clEditProfile.setOnClickListener {
            // Handle edit profile click
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        binding.clChangePassword.setOnClickListener {
            startActivity(Intent(requireContext(), ChangePasswordActivity::class.java))
        }

        binding.clPaymentHistory.setOnClickListener {
            // Handle payment history click
            startActivity(Intent(requireContext(), TransactionActivity::class.java))
        }

        binding.clLogout.setOnClickListener {
            performLogout()
        }
    }

    private fun startImageUpload() {
        // You might use an image picker library or open the device's image gallery
        val imagePickerIntent = Intent(Intent.ACTION_PICK)
        imagePickerIntent.type = "image/*"
        startActivityForResult(imagePickerIntent, IMAGE_PICK_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            // Now, you can upload the selected image to your server or storage
            uploadImage(selectedImageUri)
        }
    }

    private fun uploadImage(imageUri: Uri?) {
        // Implement the logic to upload the image to your server or storage
        // For example, you might use Firebase Storage, AWS S3, or your own server API
        // After a successful upload, you can update the user's profile picture
        // For simplicity, let's just show a toast message here
        showToast("Image uploaded successfully!")
    }

    private fun showToast(s: String) {
        TODO("Not yet implemented")
    }

    private fun performLogout() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Add other functions for image upload, logout, etc., as needed
}
