package com.example.week1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.week1.databinding.FragmentAlbumBinding
import com.example.week1.databinding.FragmentBannerBinding

class BannerFragment(val imgRes : Int) : Fragment() {
    private lateinit var binding: FragmentBannerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBannerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bannerImageIv.setImageResource(imgRes)
    }
}