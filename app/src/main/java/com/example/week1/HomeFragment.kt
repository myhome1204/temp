package com.example.week1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.week1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private var max_num :Int = 2
    private var runnable: Runnable? = null
    private var current_page : Int = 0;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
//        binding.homeAlbumImgIv1.setOnClickListener {
//            //내가 속하고 있는 activity를 부르기 위한작업 (context as MainActivity)
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.main_frm,AlbumFragment())
//                .commitAllowingStateLoss()
//        }
//        val bannerAdapter = BannerVPAdapter(this@HomeFragment)
//        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
//        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
//        binding.homeBannerVp.adapter = bannerAdapter
//        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeAlbumImgIv1.setOnClickListener {
            //내가 속하고 있는 activity를 부르기 위한작업 (context as MainActivity)
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,AlbumFragment())
                .commitAllowingStateLoss()
        }
        val bannerAdapter = BannerVPAdapter(this@HomeFragment)
        val panelAdapter  = PanelAdapter(this@HomeFragment)
        panelAdapter.addLFragment(PanelFragment(R.drawable.img_first_album_default))
        panelAdapter.addLFragment(PanelFragment(R.drawable.img_first_album_default))
        binding.homePannelBackgroundIv.adapter = panelAdapter
        binding.homePannelCircleindicator.setViewPager(binding.homePannelBackgroundIv)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val handler = Handler(Looper.getMainLooper())
        Thread {
            while (true) {
                Thread.sleep(2000)
                // UI 업데이트는 메인 스레드에서만 가능하므로 runOnUiThread를 사용하여 실행
                activity?.runOnUiThread {
                    if (current_page == max_num - 1) {
                        current_page = 0
                    } else {
                        current_page++
                    }
                    binding.homePannelBackgroundIv.setCurrentItem(current_page, true)
                }
            }
        }.start()
    }
}