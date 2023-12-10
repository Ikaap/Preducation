package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.viewitems

import android.view.View
import androidx.core.view.isGone
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ItemSectionDataCurriculcumBinding
import com.kelompoksatuandsatu.preducation.databinding.ItemSectionHeaderCurriculcumBinding
import com.kelompoksatuandsatu.preducation.model.detailcourse.ChapterViewParam
import com.kelompoksatuandsatu.preducation.model.detailcourse.VideoViewParam
import com.xwray.groupie.viewbinding.BindableItem

class HeaderItem(
    private val data: ChapterViewParam,
    private val onHeaderClick: (ChapterViewParam) -> Unit
) :
    BindableItem<ItemSectionHeaderCurriculcumBinding>() {
    override fun bind(viewBinding: ItemSectionHeaderCurriculcumBinding, position: Int) {
        viewBinding.tvTitleChapter.text = data.title
        viewBinding.tvChapterTotalDuration.text = data.totalDuration.toString() + "Mins"
        viewBinding.root.setOnClickListener { onHeaderClick.invoke(data) }
    }

    override fun getLayout(): Int = R.layout.item_section_header_curriculcum

    override fun initializeViewBinding(view: View): ItemSectionHeaderCurriculcumBinding =
        ItemSectionHeaderCurriculcumBinding.bind(view)
}

class DataItem(
    private val itemData: VideoViewParam,
    private val onItemClick: (item: VideoViewParam) -> Unit
) :
    BindableItem<ItemSectionDataCurriculcumBinding>() {
    override fun bind(viewBinding: ItemSectionDataCurriculcumBinding, position: Int) {
        viewBinding.tvVideoNumber.text = itemData.index.toString()
        viewBinding.tvTitleVideo.text = itemData.title
        viewBinding.tvDurationVideo.text = itemData.duration.toString() + "Mins"
        viewBinding.tvVideoUrl.text = itemData.videoUrl
        if (viewBinding.tvVideoUrl.text == null) {
            viewBinding.ivPlayGreen.isGone = true
            viewBinding.ivPlayOrange.isGone = true
            viewBinding.ivLock.isGone = false
        } else {
            viewBinding.ivPlayGreen.isGone = false
            viewBinding.ivPlayOrange.isGone = false
            viewBinding.ivLock.isGone = true
        }
        viewBinding.root.setOnClickListener { onItemClick.invoke(itemData) }
    }

    override fun getLayout(): Int = R.layout.item_section_data_curriculcum

    override fun initializeViewBinding(view: View): ItemSectionDataCurriculcumBinding =
        ItemSectionDataCurriculcumBinding.bind(view)
}
