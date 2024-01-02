package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.FragmentCurriculcumBinding
import com.kelompoksatuandsatu.preducation.databinding.LayoutDialogBuyClassBinding
import com.kelompoksatuandsatu.preducation.databinding.LayoutDialogFinishClassBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.viewitems.DataItem
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.viewitems.HeaderItem
import com.kelompoksatuandsatu.preducation.presentation.feature.payment.PaymentActivity
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import com.kelompoksatuandsatu.preducation.utils.toCurrencyFormat
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.android.ext.android.inject

class CurriculcumFragment : Fragment() {

    private lateinit var binding: FragmentCurriculcumBinding

    private val adapterGroupie: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    private val viewModel: DetailClassViewModel by activityViewModels()

    private val assetWrapper: AssetWrapper by inject()

    private var itemVideoId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurriculcumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnClickListener()
    }

    private fun getData() {
        val id = viewModel.detailCourse.value?.payload?.id
        id?.let {
            viewModel.getCourseById(it)
        }
    }

    private fun observeData() {
        viewModel.detailCourse.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.shimmerAboutRvChapter.isGone = true
                    binding.rvDataCurriculcum.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = adapterGroupie
                    }
                    it.payload?.let {
                        binding.clButtonEnrollClass.isVisible = it.typeClass != getString(R.string.text_free_cl)
                        if (it.chapters?.get(0)?.videos?.get(0)?.videoUrl?.isNotEmpty() == true) {
                            binding.clButtonEnrollClass.isVisible = false
                        }

                        var totalVid = 0
                        it.chapters?.forEach {
                            totalVid += it.videos?.size ?: 0
                        }

                        val section = it.chapters?.map {
                            val section = Section()
                            section.setHeader(
                                HeaderItem(it, assetWrapper) { }
                            )
                            val dataSection = it.videos?.map { data ->
                                DataItem(data, assetWrapper) {
                                    viewModel.onVideoItemClick(data.videoUrl.orEmpty())
                                    viewModel.postIndexVideo(data)

                                    if (data.index == totalVid) {
                                        showLastVideoDialog()
                                    }

                                    getData()
                                    if (it.videoUrl?.isNotEmpty() == true) {
                                        binding.clButtonEnrollClass.isVisible = false
                                    }
                                    itemVideoId = data.videoUrl.toString()
                                }
                            }
                            if (dataSection != null) {
                                section.addAll(dataSection)
                            }
                            section
                        }
                        if (section != null) {
                            adapterGroupie.clear()
                            adapterGroupie.addAll(section)
                        }
                    }
                },
                doOnEmpty = {
                    binding.shimmerAboutRvChapter.isGone = true
                },
                doOnError = {
                    binding.shimmerAboutRvChapter.isGone = true
                }
            )
        }
    }

    private fun showLastVideoDialog() {
        val binding: LayoutDialogFinishClassBinding =
            LayoutDialogFinishClassBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext(), 0).create()

        dialog.apply {
            setView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()

        binding.clContinue.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setOnClickListener() {
        binding.clButtonEnrollClass.setOnClickListener {
            setBottomSheet()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setBottomSheet() {
        val bottomDialog = BottomSheetDialog(requireContext())
        val binding = LayoutDialogBuyClassBinding.inflate(layoutInflater)

        viewModel.detailCourse.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let {
                        binding.ivBannerCourse.load(it.thumbnail)
                        binding.tvCategoryCourse.text = it.category?.name
                        binding.tvNameCourse.text = it.title
                        binding.tvTotalModulCourse.text = it.totalModule.toString() + assetWrapper.getString(R.string.text_module)
                        binding.tvTotalHourCourse.text = it.totalDuration.toString() + assetWrapper.getString(R.string.text_mins)
                        binding.tvLevelCourse.text = it.level + assetWrapper.getString(R.string.text_level)
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
