package com.kelompoksatuandsatu.preducation.presentation.feature.notifications

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.FragmentNotificationBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.notifications.adapter.NotificationAdapter
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding

    private val viewModel: NotificationViewModel by viewModel()

    private val assetWrapper: AssetWrapper by inject()

    private val viewModelModule = module {
        viewModel { NotificationViewModel(get()) }
    }

    private val notificationAdapter: NotificationAdapter by lazy {
        NotificationAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
        getData()
    }

    private fun getData() {
        viewModel.getData()
    }

    private fun setupRecyclerView() {
        binding.rvNotification.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvNotification.adapter = notificationAdapter
    }

    @SuppressLint("ResourceType")
    private fun observeData() {
        viewModel.notifications.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvNotification.isVisible = true
                    binding.layoutStateNotification.root.isVisible = false
                    binding.shimmerNotification.isVisible = false
                    binding.layoutStateNotification.pbLoading.isVisible = false
                    binding.layoutStateNotification.tvError.isVisible = false
                    binding.rvNotification.apply {
                        isVisible = true
                        adapter = notificationAdapter
                    }
                    it.payload?.let { data ->
                        notificationAdapter.setData(data)
                    }
                },
                doOnLoading = {
                    binding.rvNotification.isVisible = false
                    binding.shimmerNotification.isVisible = true
                    binding.layoutStateNotification.root.isVisible = true
                    binding.layoutStateNotification.tvError.isVisible = false
                },
                doOnError = { error ->
                    binding.rvNotification.isVisible = false
                    binding.shimmerNotification.isVisible = false
                    binding.layoutStateNotification.root.isVisible = true
                    binding.layoutStateNotification.pbLoading.isVisible = false
                    binding.layoutStateNotification.tvError.isVisible = true
                    binding.layoutStateNotification.tvError.text = error.exception?.message

                    if (it.exception is ApiException) {
                        if (it.exception.httpCode == 401) {
                            binding.layoutStateNotification.tvError.isVisible = true
                            binding.layoutStateNotification.tvError.text =
                                it.exception.getParsedError()?.message
                            binding.layoutStateNotification.tvError.setBackgroundResource(R.style.failedtoast)
                            if (it.exception.getParsedError()?.message?.contains("Login") == true) {
                                StyleableToast.makeText(
                                    requireContext(),
                                    "Login please",
                                    Toast.LENGTH_SHORT
                                ).apply {
                                    view?.setBackgroundResource(R.style.failedtoast)
                                }.show()
                            }
                        }
                    }
                },
                doOnEmpty = {
                    binding.rvNotification.isVisible = false
                    binding.layoutStateNotification.root.isVisible = true
                    binding.shimmerNotification.isVisible = false
                    binding.layoutStateNotification.pbLoading.isVisible = false
                    binding.layoutStateNotification.tvError.isVisible = true
                    binding.layoutStateNotification.tvError.text =
                        resources.getString(R.string.text_notification)
                    binding.rvNotification.isVisible = false
                }
            )
        }
    }
}
