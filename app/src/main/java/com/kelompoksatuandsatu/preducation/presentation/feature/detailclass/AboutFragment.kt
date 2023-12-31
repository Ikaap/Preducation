package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.databinding.FragmentAboutBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.adapter.DescriptionItemAdapter
import com.kelompoksatuandsatu.preducation.utils.proceedWhen

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    private val adapterDesc: DescriptionItemAdapter by lazy {
        DescriptionItemAdapter(listOf())
    }

    private val viewModel: DetailClassViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setupRv()
    }

    private fun setupRv() {
        binding.rvDescRecommendedStudents.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterDesc
        }
    }

    private fun observeData() {
        viewModel.detailCourse.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.shimmerAboutDesc.isGone = true
                    binding.shimmerAboutRecommendedStudents.isGone = true
                    binding.layoutCommonStateAbout.root.isGone = true
                    binding.layoutCommonStateAbout.tvError.isGone = true
                    binding.layoutCommonStateAbout.tvErrorState.isGone = true
                    binding.layoutCommonStateAbout.ivErrorState.isGone = true
                    binding.layoutCommonStateRecommended.root.isGone = true
                    binding.layoutCommonStateRecommended.tvError.isGone = true
                    binding.layoutCommonStateRecommended.tvErrorState.isGone = true
                    binding.layoutCommonStateRecommended.ivErrorState.isGone = true
                    binding.rvDescRecommendedStudents.apply {
                        binding.rvDescRecommendedStudents.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        adapter = adapterDesc
                    }
                    it.payload?.let {
                        binding.tvDescAboutClass.text = it.description
                        val targetAudienceList: List<String> = it.targetAudience ?: emptyList()
                        adapterDesc.setData(targetAudienceList)
                    }
                },
                doOnLoading = {
                    binding.shimmerAboutDesc.isGone = false
                    binding.shimmerAboutRecommendedStudents.isGone = false
                    binding.layoutCommonStateAbout.root.isGone = true
                    binding.layoutCommonStateAbout.tvError.isGone = true
                    binding.layoutCommonStateAbout.tvErrorState.isGone = true
                    binding.layoutCommonStateAbout.ivErrorState.isGone = true
                    binding.layoutCommonStateRecommended.root.isGone = true
                    binding.layoutCommonStateRecommended.tvError.isGone = true
                    binding.layoutCommonStateRecommended.tvErrorState.isGone = true
                    binding.layoutCommonStateRecommended.ivErrorState.isGone = true
                },
                doOnError = {
                    binding.shimmerAboutDesc.isGone = true
                    binding.shimmerAboutRecommendedStudents.isGone = true
                    binding.layoutCommonStateAbout.root.isGone = false
                    binding.layoutCommonStateAbout.tvError.isGone = false
                    binding.layoutCommonStateAbout.tvError.text =
                        it.exception?.message
                    binding.layoutCommonStateAbout.tvErrorState.isGone = true
                    binding.layoutCommonStateAbout.ivErrorState.isGone = true
                    binding.layoutCommonStateRecommended.root.isGone = false
                    binding.layoutCommonStateRecommended.tvError.isGone = false
                    binding.layoutCommonStateRecommended.tvError.text =
                        it.exception?.message
                    binding.layoutCommonStateRecommended.tvErrorState.isGone = true
                    binding.layoutCommonStateRecommended.ivErrorState.isGone = true
                },
                doOnEmpty = {
                    binding.shimmerAboutDesc.isGone = true
                    binding.shimmerAboutRecommendedStudents.isGone = true
                    binding.layoutCommonStateAbout.root.isGone = false
                    binding.layoutCommonStateAbout.tvError.isGone = false
                    binding.layoutCommonStateAbout.tvError.text = "data ksoong"
                    binding.layoutCommonStateAbout.tvErrorState.isGone = true
                    binding.layoutCommonStateAbout.ivErrorState.isGone = true
                    binding.layoutCommonStateRecommended.root.isGone = false
                    binding.layoutCommonStateRecommended.tvError.isGone = false
                    binding.layoutCommonStateRecommended.tvError.text = "data ksoong"
                    binding.layoutCommonStateRecommended.tvErrorState.isGone = true
                    binding.layoutCommonStateRecommended.ivErrorState.isGone = true
                }
            )
        }
    }
}
