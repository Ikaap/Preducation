package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kelompoksatuandsatu.preducation.databinding.FragmentCurriculcumBinding
import com.kelompoksatuandsatu.preducation.databinding.LayoutDialogBuyClassBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.viewitems.DataItem
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.viewitems.HeaderItem
import com.kelompoksatuandsatu.preducation.presentation.feature.payment.PaymentActivity
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import com.kelompoksatuandsatu.preducation.utils.toCurrencyFormat
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section

class CurriculcumFragment : Fragment() {

    private lateinit var binding: FragmentCurriculcumBinding

    private val adapterGroupie: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    private val viewModel: DetailClassViewModel by activityViewModels()

    private var itemVideoId: String = ""
    private var currentVideoPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurriculcumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnClickListener()
    }

    private fun observeData() {
        viewModel.detailCourse.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.shimmerAboutRvChapter.isGone = true
                    binding.layoutCommonState.root.isGone = true
                    binding.layoutCommonState.tvError.isGone = true
                    binding.layoutCommonState.tvDataEmpty.isGone = true
                    binding.layoutCommonState.ivDataEmpty.isGone = true
                    binding.rvDataCurriculcum.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = adapterGroupie
                    }
                    it.payload?.let {
                        val section = it.chapters?.map {
                            val section = Section()
                            section.setHeader(
                                HeaderItem(it) { data ->
                                    Toast.makeText(
                                        requireContext(),
                                        "Header Clicked : ${data.title}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                            val dataSection = it.videos?.map { data ->
                                DataItem(data) {
                                    itemVideoId = data.videoUrl.toString()
                                    Toast.makeText(
                                        requireContext(),
                                        "Item clicked : title = ${data.title} -> url = ${data.videoUrl}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // klik video
                                    viewModel.onVideoItemClick(data.videoUrl.orEmpty())
                                    viewModel.postIndexVideo(data)
                                }
                            }
                            if (dataSection != null) {
                                section.addAll(dataSection)
                            }
                            section
                        }
                        if (section != null) {
                            adapterGroupie.addAll(section)
                        }
                    }
                },
                doOnLoading = {
                    binding.shimmerAboutRvChapter.isGone = false
                    binding.layoutCommonState.root.isGone = true
                    binding.layoutCommonState.clDataEmpty.isGone = true
                    binding.layoutCommonState.tvError.isGone = true
                    binding.layoutCommonState.tvDataEmpty.isGone = true
                    binding.layoutCommonState.ivDataEmpty.isGone = true
                },
                doOnEmpty = {
                    binding.shimmerAboutRvChapter.isGone = true
                    binding.layoutCommonState.root.isGone = false
                    binding.layoutCommonState.clDataEmpty.isGone = true
                    binding.layoutCommonState.tvError.isGone = false
                    binding.layoutCommonState.tvError.text = "data kosong"
                    binding.layoutCommonState.tvDataEmpty.isGone = true
                    binding.layoutCommonState.ivDataEmpty.isGone = true
                },
                doOnError = {
                    binding.shimmerAboutRvChapter.isGone = true
                    binding.layoutCommonState.root.isGone = false
                    binding.layoutCommonState.clDataEmpty.isGone = true
                    binding.layoutCommonState.tvError.isGone = false
                    binding.layoutCommonState.tvError.text =
                        it.exception?.message + "${it.payload?.id}"
                    binding.layoutCommonState.tvDataEmpty.isGone = true
                    binding.layoutCommonState.ivDataEmpty.isGone = true
                }
            )
        }
    }

    private fun setOnClickListener() {
        binding.clButtonEnrollClass.setOnClickListener {
            setBottomSheet()
        }
    }

    private fun setBottomSheet() {
        val bottomDialog = BottomSheetDialog(requireContext())
        val binding = LayoutDialogBuyClassBinding.inflate(layoutInflater)

        // set data item course
        viewModel.detailCourse.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let {
                        binding.ivBannerCourse.load(it.thumbnail)
                        binding.tvCategoryCourse.text = it.category?.name
                        binding.tvNameCourse.text = it.title
                        binding.tvTotalModulCourse.text = it.totalModule.toString() + " Module"
                        binding.tvTotalHourCourse.text = it.totalDuration.toString() + " Mins"
                        binding.tvLevelCourse.text = it.level + " Level"
                        binding.tvCourseRating.text = it.totalRating.toString()
                        binding.tvPriceCourse.text = it.price?.toCurrencyFormat()
                    }
                }
            )
        }

        bottomDialog.apply {
            setContentView(binding.root)
            show()
        }
        binding.clButtonCancel.setOnClickListener {
            bottomDialog.dismiss()
        }
        binding.clButtonEnroll.setOnClickListener {
            val data = viewModel.detailCourse.value?.payload

            val intent = Intent(requireContext(), PaymentActivity::class.java)
            data?.let {
                intent.putExtra("EXTRA_DETAIL_COURSE", it)
            }
            requireContext().startActivity(intent)
        }
    }
}
