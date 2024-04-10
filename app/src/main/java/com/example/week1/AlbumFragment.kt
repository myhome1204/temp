package com.example.week1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.week1.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.albumBackIv.setOnClickListener {
            //내가 속하고 있는 activity를 부르기 위한작업 (context as MainActivity)
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,HomeFragment())
                .commitAllowingStateLoss()
        }
        binding.songLalacLayout.setOnClickListener {
            Toast.makeText(activity,"LILAC",Toast.LENGTH_SHORT).show()
        }
    }
}