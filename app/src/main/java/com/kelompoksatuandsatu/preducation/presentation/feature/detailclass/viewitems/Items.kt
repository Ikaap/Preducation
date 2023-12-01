package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.viewitems

import android.view.View
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ItemSectionDataCurriculcumBinding
import com.kelompoksatuandsatu.preducation.databinding.ItemSectionHeaderCurriculcumBinding
import com.kelompoksatuandsatu.preducation.model.ItemSectionDataCurriculcum
import com.kelompoksatuandsatu.preducation.model.ItemSectionHeaderCurriculcum
import com.xwray.groupie.viewbinding.BindableItem

class HeaderItem(
    private val data: ItemSectionHeaderCurriculcum,
    private val onHeaderClick: (ItemSectionHeaderCurriculcum) -> Unit
) :
    BindableItem<ItemSectionHeaderCurriculcumBinding>() {
    override fun bind(viewBinding: ItemSectionHeaderCurriculcumBinding, position: Int) {
        viewBinding.tvTitleChapter.text = data.title
        viewBinding.tvChapterTotalDuration.text = data.duration.toString() + " Mins"
        viewBinding.root.setOnClickListener { onHeaderClick.invoke(data) }
    }

    override fun getLayout(): Int = R.layout.item_section_header_curriculcum

    override fun initializeViewBinding(view: View): ItemSectionHeaderCurriculcumBinding =
        ItemSectionHeaderCurriculcumBinding.bind(view)
}

class DataItem(
    private val itemData: ItemSectionDataCurriculcum,
    private val onItemClick: (item: ItemSectionDataCurriculcum) -> Unit
) :
    BindableItem<ItemSectionDataCurriculcumBinding>() {
    override fun bind(viewBinding: ItemSectionDataCurriculcumBinding, position: Int) {
        viewBinding.tvVideoNumber.text = itemData.number
        viewBinding.tvTitleVideo.text = itemData.title
        viewBinding.tvDurationVideo.text = itemData.duration.toString() + " Mins"
        viewBinding.root.setOnClickListener { onItemClick.invoke(itemData) }
    }

    override fun getLayout(): Int = R.layout.item_section_data_curriculcum

    override fun initializeViewBinding(view: View): ItemSectionDataCurriculcumBinding =
        ItemSectionDataCurriculcumBinding.bind(view)
}
