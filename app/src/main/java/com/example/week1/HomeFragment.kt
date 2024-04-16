package com.example.week1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.week1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
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
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }
}