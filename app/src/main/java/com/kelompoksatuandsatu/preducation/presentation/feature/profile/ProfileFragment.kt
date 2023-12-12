package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.FragmentProfileBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.changepassword.ChangePasswordActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.editprofile.EditProfileActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.historypayment.TransactionActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import org.koin.android.ext.android.inject
import java.io.FileNotFoundException
import java.io.InputStream

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    private val assetWrapper: AssetWrapper by inject()

    private val RESULT_LOAD_IMG = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupClickListeners() {
        binding.ivUserPhoto.setOnClickListener {
            startImageUpload()
        }

        binding.clEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        binding.clChangePassword.setOnClickListener {
            startActivity(Intent(requireContext(), ChangePasswordActivity::class.java))
        }

        binding.clPaymentHistory.setOnClickListener {
            startActivity(Intent(requireContext(), TransactionActivity::class.java))
        }

        binding.clLogout.setOnClickListener {
            performLogout()
        }
    }

    private fun startImageUpload() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK) {
            try {
                val imageUri: Uri? = data?.data
                val imageStream: InputStream? = imageUri?.let {
                    requireActivity().contentResolver.openInputStream(it)
                }
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                binding.ivUserPhoto.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(requireContext(), "You haven't picked an image", Toast.LENGTH_LONG).show()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun performLogout() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                assetWrapper.getString(R.string.text_logout_dialog)
            )
            .setPositiveButton(assetWrapper.getString(R.string.text_yes)) { _, _ ->
                viewModel.performLogout()
                navigateToLogin()
            }.setNegativeButton(assetWrapper.getString(R.string.text_no)) { _, _ ->
            }.create().show()
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}
