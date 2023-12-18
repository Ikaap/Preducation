package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.FragmentProfileBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.changepassword.ChangePasswordActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.editprofile.EditProfileActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.historypayment.TransactionActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModel()

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

        setDataProfile()
        setupClickListeners()
        getData()
    }

    private fun setupClickListeners() {
        binding.clEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
//            val userId: String? = viewModel.getUserById().toString()
//            userId?.let {
//                val intent = Intent(requireContext(), EditProfileActivity::class.java)
//                intent.putExtra(EditProfileActivity.EXTRA_USER_ID, it)
//                startActivity(intent)
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

    private fun setDataProfile() {
        viewModel.getProfile.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnLoading = {
                    binding.root.isVisible = true
                    binding.ivUserPhoto.isVisible = false
                },
                doOnError = {
                    binding.root.isVisible = true
                    binding.ivUserPhoto.isVisible = false
                },
                doOnSuccess = {
                    binding.root.isVisible = true
                    binding.ivUserPhoto.isVisible = true
                    binding.tvLongName.text = it.payload?.name.orEmpty()
                    binding.tvEmail.text = it.payload?.email.orEmpty()
                    binding.ivUserPhoto.load(it.payload?.imageProfile.orEmpty())
                }
            )
        }
    }

    private fun getData() {
        viewModel.getUserById()
//        val userId = arguments?.getString(EXTRA_USER_ID)
//        userId?.let { viewModel.getUserById(it) }
    }
    private fun performLogout() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.text_logout_dialog)) // Using getString directly
            .setPositiveButton(getString(R.string.text_yes)) { _, _ ->
//                viewModel.performLogout()
                navigateToLogin()
            }
            .setNegativeButton(getString(R.string.text_no)) { _, _ ->
                // Handle negative button click if needed
            }
            .create()
            .show()
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

//    companion object {
//        const val EXTRA_USER_ID = "EXTRA_USER_ID"
//
//        fun newInstance(userId: String): ProfileFragment {
//            val fragment = ProfileFragment()
//            val args = Bundle()
//            args.putString(EXTRA_USER_ID, userId)
//            fragment.arguments = args
//            return fragment
//        }
//    }
}
