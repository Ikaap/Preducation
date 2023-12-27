package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.FragmentProfileBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.changepassword.ChangePasswordActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.editprofile.EditProfileActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.historypayment.HistoryPaymentActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
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

        getData()
        setDataProfile()
        setupClickListeners()
        observeLogoutResult()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserById()
        setDataProfile()
    }

    private fun getData() {
        viewModel.getUserById()
    }

    private fun setDataProfile() {
        viewModel.getProfile.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.root.isVisible = true
                    binding.ivUserPhoto.isVisible = true
                    it.payload?.let {
                        binding.tvLongName.text = it.name.orEmpty()
                        binding.tvEmail.text = it.email.orEmpty()
                        binding.ivUserPhoto.load(it.imageProfile.orEmpty())
                    }
                },
                doOnLoading = {
                    binding.root.isVisible = true
                    binding.ivUserPhoto.isVisible = false
                },
                doOnError = {
                    binding.root.isVisible = true
                    binding.ivUserPhoto.isVisible = false
                    binding.clDataPersonal.isVisible = false

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorProfile()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clServerError.isGone = false
                                binding.layoutCommonState.ivServerError.isGone = false
                                StyleableToast.makeText(
                                    requireContext(),
                                    "SERVER ERROR",
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorProfile()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorProfile()?.message
                                StyleableToast.makeText(
                                    requireContext(),
                                    it.exception.getParsedErrorProfile()?.message,
                                    R.style.failedtoast
                                ).show()
                            }
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(requireContext())) {
                            binding.layoutCommonState.clNoConnection.isGone = false
                            binding.layoutCommonState.ivNoConnection.isGone = false
                            StyleableToast.makeText(
                                requireContext(),
                                "tidak ada internet",
                                R.style.failedtoast
                            ).show()
                        }
                    }
                }
            )
        }
    }

    private fun setupClickListeners() {
        binding.clEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        binding.clChangePassword.setOnClickListener {
            startActivity(Intent(requireContext(), ChangePasswordActivity::class.java))
        }

        binding.clPaymentHistory.setOnClickListener {
            startActivity(Intent(requireContext(), HistoryPaymentActivity::class.java))
        }

        binding.clLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.text_logout_dialog))
            .setPositiveButton(getString(R.string.text_yes)) { _, _ ->
                viewModel.userLogout()
            }
            .setNegativeButton(getString(R.string.text_no)) { _, _ ->
            }
            .create()
            .show()
    }

    private fun observeLogoutResult() {
        viewModel.logoutResults.observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    StyleableToast.makeText(
                        requireContext(),
                        "Successfully Logout",
                        R.style.successtoast
                    ).show()
                    performLogout()
                },
                doOnError = {
                    StyleableToast.makeText(
                        requireContext(),
                        "Failed to Logout",
                        R.style.failedtoast
                    ).show()
                },
                doOnLoading = {
                    StyleableToast.makeText(
                        requireContext(),
                        "Loading Logout",
                        R.style.successtoast
                    ).show()
                },
                doOnEmpty = {
                    StyleableToast.makeText(
                        requireContext(),
                        "Empty Logout",
                        R.style.failedtoast
                    ).show()
                }
            )
        }
    }

    private fun performLogout() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}
