package com.kelompoksatuandsatu.preducation.presentation.feature.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.FragmentNotificationBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.notifications.adapter.NotificationAdapter
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding

    private val viewModel: NotificationViewModel by viewModel()

//    private val assetWrapper: AssetWrapper by inject()
//
//    private val viewModelModule = module {
//        viewModel { NotificationViewModel(get()) }
//    }

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

    private fun observeData() {
        viewModel.notifications.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { data ->
                    binding.rvNotification.isVisible = true
                    binding.layoutStateNotification.root.isVisible = false
                    binding.layoutStateNotification.tvError.isVisible = false
                    binding.layoutStateNotification.pbLoading.isVisible = false
                    binding.rvNotification.apply {
                        adapter = notificationAdapter
                    }

                    data.payload?.let { notificationData ->
                        notificationAdapter.setData(notificationData)
                    }
                },
                doOnLoading = {
                    binding.rvNotification.isVisible = false
                    binding.layoutStateNotification.root.isVisible = true
                    binding.layoutStateNotification.tvError.isVisible = false
                    binding.layoutStateNotification.pbLoading.isVisible = true
                },
                doOnError = { error ->
                    binding.rvNotification.isVisible = false
                    binding.layoutStateNotification.root.isVisible = true
                    binding.layoutStateNotification.tvError.isVisible = true
                    binding.layoutStateNotification.tvError.text = error.exception?.message
                    binding.layoutStateNotification.pbLoading.isVisible = false
                },
                doOnEmpty = {
                    binding.rvNotification.isVisible = false
                    binding.layoutStateNotification.root.isVisible = true
                    binding.layoutStateNotification.tvError.isVisible = true
                    binding.layoutStateNotification.tvError.text =
                        resources.getString(R.string.text_notification)
                    binding.rvNotification.isVisible = false
                    binding.layoutStateNotification.pbLoading.isVisible = false
                }
            )
        }
    }
}
