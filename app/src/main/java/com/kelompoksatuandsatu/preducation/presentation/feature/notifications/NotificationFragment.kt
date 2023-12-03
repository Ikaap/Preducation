package com.kelompoksatuandsatu.preducation.presentation.feature.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.FragmentNotificationBinding
import com.kelompoksatuandsatu.preducation.model.NotificationItem
import com.kelompoksatuandsatu.preducation.presentation.feature.notifications.adapter.NotificationAdapter

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var notificationAdapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        loadDummyData()
    }

    private fun initRecyclerView() {
        binding.rvNotification.layoutManager = LinearLayoutManager(requireContext())
        notificationAdapter = NotificationAdapter()
        binding.rvNotification.adapter = notificationAdapter
    }

    private fun loadDummyData() {
        val dummyData = createDummyData()
        notificationAdapter.submitList(dummyData)
    }

    private fun createDummyData(): List<NotificationItem> {
        // Create a list of dummy notifications
        val notifications = mutableListOf<NotificationItem>()

        notifications.add(
            NotificationItem(
                1,
                "Today",
                R.drawable.ic_notification_one,
                "New Category Course!",
                "New the 3D Design Course is Available."
            )
        )

        notifications.add(
            NotificationItem(
                2,
                "Today",
                R.drawable.ic_notification_two,
                "Today’s Special Offers",
                "You Have made a Course Payment."
            )
        )

        notifications.add(
            NotificationItem(
                3,
                "Yesterday",
                R.drawable.ic_notification_three,
                "Credit Card Connected!",
                "Credit Card has been Linked."
            )
        )

        return notifications
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
