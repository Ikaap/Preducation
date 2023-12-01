package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.databinding.FragmentAboutBinding
import com.kelompoksatuandsatu.preducation.model.DescriptionRecommendedStudents
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.adapter.DescriptionItemAdapter

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    private val adapterDesc: DescriptionItemAdapter by lazy {
        DescriptionItemAdapter()
    }

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

        setDataDummy()
    }

    private fun setDataDummy() {
        binding.rvDescRecommendedStudents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapterDesc.setData(dummyData)
            adapter = adapterDesc
        }
    }

    private val dummyData: List<DescriptionRecommendedStudents> = listOf(
        DescriptionRecommendedStudents("Anda yang ingin memahami poin penting design system"),
        DescriptionRecommendedStudents("Anda yang ingin memahami poin penting design system"),
        DescriptionRecommendedStudents("Anda yang ingin memahami poin penting design system"),
        DescriptionRecommendedStudents("Anda yang ingin memahami poin penting design system"),
        DescriptionRecommendedStudents("Anda yang ingin memahami poin penting design system")
    )
}
