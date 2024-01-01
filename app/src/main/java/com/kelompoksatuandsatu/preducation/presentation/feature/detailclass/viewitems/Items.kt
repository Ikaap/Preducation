package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.viewitems

import android.view.View
import androidx.core.view.isGone
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ItemSectionDataCurriculcumBinding
import com.kelompoksatuandsatu.preducation.databinding.ItemSectionHeaderCurriculcumBinding
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.ChapterViewParam
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.VideoViewParam
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.xwray.groupie.viewbinding.BindableItem
class HeaderItem(
    private val data: ChapterViewParam,
    private val assetWrapper: AssetWrapper,
    private val onHeaderClick: (ChapterViewParam) -> Unit
) :
    BindableItem<ItemSectionHeaderCurriculcumBinding>() {
    override fun bind(viewBinding: ItemSectionHeaderCurriculcumBinding, position: Int) {
        viewBinding.tvTitleChapter.text = data.title
        viewBinding.tvChapterTotalDuration.text = data.totalDuration.toString() + assetWrapper.getString(R.string.text_mins)
        viewBinding.root.setOnClickListener { onHeaderClick.invoke(data) }
    }

    override fun getLayout(): Int = R.layout.item_section_header_curriculcum

    override fun initializeViewBinding(view: View): ItemSectionHeaderCurriculcumBinding =
        ItemSectionHeaderCurriculcumBinding.bind(view)
}

class DataItem(
    private val itemData: VideoViewParam,
    private val assetWrapper: AssetWrapper,
    private val onItemClick: (item: VideoViewParam) -> Unit
) :
    BindableItem<ItemSectionDataCurriculcumBinding>() {
    override fun bind(viewBinding: ItemSectionDataCurriculcumBinding, position: Int) {
        viewBinding.tvVideoNumber.text = itemData.index.toString()
        viewBinding.tvTitleVideo.text = itemData.title
        viewBinding.tvDurationVideo.text = itemData.duration.toString() + assetWrapper.getString(R.string.text_mins)
        viewBinding.tvVideoUrl.text = itemData.videoUrl
        if (itemData.videoUrl.isNullOrEmpty()) {
            viewBinding.ivPlayGreen.isGone = true
            viewBinding.ivPlayOrange.isGone = true
            viewBinding.ivLock.isGone = false
        } else if (itemData.isWatch == true) {
            viewBinding.ivPlayGreen.isGone = false
            viewBinding.ivPlayOrange.isGone = true
            viewBinding.ivLock.isGone = true
        } else {
            viewBinding.ivPlayGreen.isGone = false
            viewBinding.ivPlayOrange.isGone = false
            viewBinding.ivLock.isGone = true
        }

        val indexNow = itemData.index

        for (i in indexNow.toString()) {
            if (indexNow.toInt() == 1 || itemData.isWatch == true) {
                viewBinding.root.setOnClickListener {
                    onItemClick.invoke(itemData)
                }
                continue
            } else if (itemData.nextVideo == true) {
                viewBinding.root.setOnClickListener {
                    onItemClick.invoke(itemData)
                }
            }
            break
        }
    }

    override fun getLayout(): Int = R.layout.item_section_data_curriculcum

    override fun initializeViewBinding(view: View): ItemSectionDataCurriculcumBinding =
        ItemSectionDataCurriculcumBinding.bind(view)
}
