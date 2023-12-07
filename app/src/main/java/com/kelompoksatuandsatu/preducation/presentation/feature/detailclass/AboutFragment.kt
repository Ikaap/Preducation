package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.databinding.FragmentAboutBinding
import com.kelompoksatuandsatu.preducation.model.toTargetAudience
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.adapter.DescriptionItemAdapter
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    private val adapterDesc: DescriptionItemAdapter by lazy {
        DescriptionItemAdapter()
    }

    private val viewModel: DetailClassViewModel by viewModel()

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

//        setDataDummy()
        setData()
        observeData()
    }

    private fun observeData() {
        viewModel.detailCourse.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
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
                        adapterDesc.setData(listOf(it.toTargetAudience()))
                    }
                }
            )
        }
    }

    private fun setData() {
        viewModel.getCourseById()
    }

//    private fun setDataDummy() {
//        binding.rvDescRecommendedStudents.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapterDesc.setData(dummyData)
//            adapter = adapterDesc
//        }
//    }
//
//    private val dummyData: List<TargetAudience> = listOf(
//        TargetAudience("Anda yang ingin memahami poin penting design system"),
//        TargetAudience("Anda yang ingin memahami poin penting design system"),
//        TargetAudience("Anda yang ingin memahami poin penting design system"),
//        TargetAudience("Anda yang ingin memahami poin penting design system"),
//        TargetAudience("Anda yang ingin memahami poin penting design system")
//    )
}
