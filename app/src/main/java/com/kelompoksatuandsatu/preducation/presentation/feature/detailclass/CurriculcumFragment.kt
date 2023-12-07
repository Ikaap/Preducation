package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kelompoksatuandsatu.preducation.databinding.FragmentCurriculcumBinding
import com.kelompoksatuandsatu.preducation.databinding.LayoutDialogBuyClassBinding
import com.kelompoksatuandsatu.preducation.model.ItemSectionDataCurriculcum
import com.kelompoksatuandsatu.preducation.model.ItemSectionHeaderCurriculcum
import com.kelompoksatuandsatu.preducation.model.SectionedCurriculcumData
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.viewitems.DataItem
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.viewitems.HeaderItem
import com.kelompoksatuandsatu.preducation.presentation.feature.payment.PaymentActivity
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurriculcumFragment : Fragment() {

    private lateinit var binding: FragmentCurriculcumBinding

    private val adapterGroupie: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    private val viewModel: DetailClassViewModel by viewModel()

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

        setData()
        observeData()
        setOnClickListener()
    }

    private fun observeData() {
        viewModel.detailCourse.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvDataCurriculcum.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = adapterGroupie
                    }
                    it.payload?.let {
                        val section = it.chapters.map {
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
                                    Toast.makeText(
                                        requireContext(),
                                        "Item clicked : title = ${data.title} -> url = ${data.videoUrl}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            if (dataSection != null) {
                                section.addAll(dataSection)
                            }
                            section
                        }
                        adapterGroupie.addAll(section)
                    }
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
        bottomDialog.apply {
            setContentView(binding.root)
            show()
        }
        binding.clButtonCancel.setOnClickListener {
            bottomDialog.dismiss()
        }

        binding.clButtonEnroll.setOnClickListener {
            // TODO Intent to payment activity
            PaymentActivity.startActivity(requireContext())
        }
    }

    private fun setData() {
        viewModel.getCourseById()

//        binding.rvDataCurriculcum.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = adapterGroupie
//        }
//
//        val section = getListData().map {
//            val section = Section()
//            section.setHeader(
//                HeaderItem(it.header) { data ->
//                    Toast.makeText(
//                        requireContext(),
//                        "Header Clicked : ${data.title}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            )
//            val dataSection = it.data.map { data ->
//                DataItem(data) { data ->
//                    Toast.makeText(
//                        requireContext(),
//                        "Item clicked : ${data.title}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//            section.addAll(dataSection)
//            section
//        }
//        adapterGroupie.addAll(section)
    }

    private fun getListData(): List<SectionedCurriculcumData> = listOf(
        SectionedCurriculcumData(
            ItemSectionHeaderCurriculcum("Chapter 1 - Introduction", 25),
            listOf(
                ItemSectionDataCurriculcum("01", "Pengenalan Design System", 10, true),
                ItemSectionDataCurriculcum(
                    "02",
                    "Tujuan Mengikuti Kelas Design System",
                    5,
                    false
                ),
                ItemSectionDataCurriculcum(
                    "03",
                    "Contoh Dalam Membangun Design System",
                    10,
                    false
                )
            )
        ),
        SectionedCurriculcumData(
            ItemSectionHeaderCurriculcum("Chapter 2 - Graphic Design", 55),
            listOf(
                ItemSectionDataCurriculcum("04", "Video 1", 30, false),
                ItemSectionDataCurriculcum("05", "Video 2", 25, false)
            )
        )
    )
}
