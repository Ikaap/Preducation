package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.FragmentAboutBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.adapter.DescriptionItemAdapter
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast

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
        // Inflate the layout for this fragment
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
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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
                    binding.layoutCommonStateAbout.tvDataEmpty.isGone = true
                    binding.layoutCommonStateAbout.ivDataEmpty.isGone = true
                    binding.layoutCommonStateRecommended.root.isGone = true
                    binding.layoutCommonStateRecommended.tvError.isGone = true
                    binding.layoutCommonStateRecommended.tvDataEmpty.isGone = true
                    binding.layoutCommonStateRecommended.ivDataEmpty.isGone = true
                    binding.rvDescRecommendedStudents.apply {
                        binding.rvDescRecommendedStudents.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        adapter = adapterDesc
                    }
                    it.payload?.let {
                        val targetAudienceList: List<String> = it.targetAudience ?: emptyList()
                        binding.tvDescAboutClass.text = it.description
                        adapterDesc.setData(targetAudienceList)
                    }
                },
                doOnLoading = {
                    binding.shimmerAboutDesc.isGone = false
                    binding.shimmerAboutRecommendedStudents.isGone = false
                    binding.layoutCommonStateAbout.root.isGone = true
                    binding.layoutCommonStateAbout.tvError.isGone = true
                    binding.layoutCommonStateAbout.tvDataEmpty.isGone = true
                    binding.layoutCommonStateAbout.ivDataEmpty.isGone = true
                    binding.layoutCommonStateRecommended.root.isGone = true
                    binding.layoutCommonStateRecommended.tvError.isGone = true
                    binding.layoutCommonStateRecommended.tvDataEmpty.isGone = true
                    binding.layoutCommonStateRecommended.ivDataEmpty.isGone = true
                },
                doOnError = {
                    binding.shimmerAboutDesc.isGone = true
                    binding.shimmerAboutRecommendedStudents.isGone = true
                    binding.layoutCommonStateAbout.root.isGone = false
                    binding.layoutCommonStateAbout.tvError.isGone = false
                    binding.layoutCommonStateAbout.tvError.text =
                        it.exception?.message + "${it.payload?.id}"
                    binding.layoutCommonStateAbout.tvDataEmpty.isGone = true
                    binding.layoutCommonStateAbout.ivDataEmpty.isGone = true
                    binding.layoutCommonStateRecommended.root.isGone = false
                    binding.layoutCommonStateRecommended.tvError.isGone = false
                    binding.layoutCommonStateRecommended.tvError.text =
                        it.exception?.message + "${it.payload?.id}"
                    binding.layoutCommonStateRecommended.tvDataEmpty.isGone = true
                    binding.layoutCommonStateRecommended.ivDataEmpty.isGone = true

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorCourse()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clServerError.isGone = false
                                binding.layoutCommonState.ivServerError.isGone = false
                                StyleableToast.makeText(
                                    requireContext(),
                                    "SERVER ERROR",
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorCourse()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorCourse()?.message
                                StyleableToast.makeText(
                                    requireContext(),
                                    it.exception.getParsedErrorCourse()?.message,
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
                },
                doOnEmpty = {
                    binding.shimmerAboutDesc.isGone = true
                    binding.shimmerAboutRecommendedStudents.isGone = true
                    binding.layoutCommonStateAbout.root.isGone = false
                    binding.layoutCommonStateAbout.tvError.isGone = false
                    binding.layoutCommonStateAbout.tvError.text = "data ksoong"
                    binding.layoutCommonStateAbout.tvDataEmpty.isGone = true
                    binding.layoutCommonStateAbout.ivDataEmpty.isGone = true
                    binding.layoutCommonStateRecommended.root.isGone = false
                    binding.layoutCommonStateRecommended.tvError.isGone = false
                    binding.layoutCommonStateRecommended.tvError.text = "data ksoong"
                    binding.layoutCommonStateRecommended.tvDataEmpty.isGone = true
                    binding.layoutCommonStateRecommended.ivDataEmpty.isGone = true
                }
            )
        }
    }
}
