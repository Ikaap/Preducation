package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isGone
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityDetailClassBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.adapter.ViewPagerAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions

class DetailClassActivity : AppCompatActivity() {

    private val binding: ActivityDetailClassBinding by lazy {
        ActivityDetailClassBinding.inflate(layoutInflater)
    }

    private val adapterViewPager: ViewPagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager, lifecycle)
    }

    private var isFullScreen = false
    private lateinit var youTubePlayer: YouTubePlayer

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isFullScreen) {
                youTubePlayer.toggleFullscreen()
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setOnClickListener()
        setYoutubeFullScreen()
        setLayoutViewPager()
    }

    private fun setOnClickListener() {
        binding.ivBack.setOnClickListener {
            // TODO Intent to class or course or home
        }
    }

    private fun setYoutubeFullScreen() {
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                setViewEnterFullScreen(fullscreenView)

                WindowInsetsControllerCompat(
                    window!!,
                    findViewById(R.id.cl_detail_class_activity)
                ).apply {
                    hide(WindowInsetsCompat.Type.systemBars())
                    systemBarsBehavior =
                        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }

                if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                }
            }

            override fun onExitFullscreen() {
                setViewExitFullScreen()

                WindowInsetsControllerCompat(
                    window!!,
                    findViewById(R.id.cl_detail_class_activity)
                ).apply {
                    show(WindowInsetsCompat.Type.systemBars())
                }

                if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                }
            }
        })

        val youtubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                this@DetailClassActivity.youTubePlayer = youTubePlayer
                val videoId = "LJY5M2eXDRM"
                youTubePlayer.cueVideo(videoId, 0.0F)
                binding.ivPlayVideo.setOnClickListener {
                    youTubePlayer.play()
                    binding.ivPlayVideo.isGone = true
                }

                youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {
                        super.onStateChange(youTubePlayer, state)
                        setImagePlayVisibility(state)
                    }
                })
            }
        }

        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1)
            .build()

        binding.youtubePlayerView.enableAutomaticInitialization = false
        binding.youtubePlayerView.initialize(youtubePlayerListener, iFramePlayerOptions)
    }

    private fun setImagePlayVisibility(state: PlayerConstants.PlayerState) {
        when (state) {
            PlayerConstants.PlayerState.ENDED -> {
                binding.ivPlayVideo.isGone = false
                binding.tvButtonNext.isGone = false
                binding.tvButtonOtherClass.isGone = false
            }

            PlayerConstants.PlayerState.PAUSED -> {
                binding.ivPlayVideo.isGone = false
                binding.tvButtonNext.isGone = false
                binding.tvButtonOtherClass.isGone = false
            }

            PlayerConstants.PlayerState.PLAYING -> {
                binding.ivPlayVideo.isGone = true
                binding.tvButtonNext.isGone = true
                binding.tvButtonOtherClass.isGone = true
            }

            else -> {}
        }
    }

    private fun setViewEnterFullScreen(fullscreenView: View) {
        isFullScreen = true
        binding.flFullScreen.visibility = View.VISIBLE
        binding.flFullScreen.addView(fullscreenView)
        binding.clDataCourse.visibility = View.GONE
        binding.viewBackground.visibility = View.GONE

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.clYoutubePlayer)

        constraintSet.clear(R.id.iv_play_video, ConstraintSet.BOTTOM)
        constraintSet.connect(
            R.id.iv_play_video,
            ConstraintSet.BOTTOM,
            R.id.cl_youtube_player,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(binding.clYoutubePlayer)
    }

    private fun setViewExitFullScreen() {
        isFullScreen = false
        binding.flFullScreen.visibility = View.GONE
        binding.flFullScreen.removeAllViews()
        binding.clDataCourse.visibility = View.VISIBLE
        binding.viewBackground.visibility = View.VISIBLE

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.clYoutubePlayer)

        constraintSet.clear(R.id.iv_play_video, ConstraintSet.BOTTOM)
        constraintSet.connect(
            R.id.iv_play_video,
            ConstraintSet.BOTTOM,
            R.id.youtube_player_view,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(binding.clYoutubePlayer)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (!isFullScreen) {
                youTubePlayer.toggleFullscreen()
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                if (isFullScreen) {
                    youTubePlayer.toggleFullscreen()
                }
            }
        }
    }

    private fun setLayoutViewPager() {
        with(binding) {
            tabLayout.addTab(tabLayout.newTab().setText("About"))
            tabLayout.addTab(tabLayout.newTab().setText("Curriculcum"))

            viewPager.adapter = adapterViewPager

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                @SuppressLint("UseCompatLoadingForDrawables")
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        viewPager.currentItem = tab.position
                        tab?.view?.background = getDrawable(R.drawable.selected_tab_background)
                    }
                }

                @SuppressLint("UseCompatLoadingForDrawables")
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.view?.background = getDrawable(R.drawable.unselected_tab_background)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }
    }
}
