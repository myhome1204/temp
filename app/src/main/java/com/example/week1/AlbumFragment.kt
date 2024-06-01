package com.example.week1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.week1.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡","상세정보","영상")
    private var gson : Gson = Gson()
    private var isLiked : Boolean = false
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
        val albumAdapter =AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb,binding.albumContentVp){
            tab,position->
            tab.text = information[position]
        }.attach()
        val albumJson  = arguments?.getString("album")
        val album = gson.fromJson(albumJson,Album::class.java)
        isLiked = isLikedAlbum(album.id)
        setinit(album)
        setOnClickListeners(album)
    }
    fun setinit(album:Album){
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title
        binding.albumSingerNameTv.text = album.singer
        if(isLiked ==true){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }
    private fun getJwt() : Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt",0)
    }

    private  fun likeAlbum(userId : Int,albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like =  Like(userId,albumId)
        songDB.albumDao().likeAlbum(like)
    }

    private fun isLikedAlbum(albumId : Int) : Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        var userId = getJwt()
        val likedId : Int? = songDB.albumDao().isLikedAlbum(userId,albumId)

        return likedId != null
    }

    private fun disLikedAlbum(albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        var userId = getJwt()
        songDB.albumDao().disLikedAlbum(userId,albumId)
    }

    private fun setOnClickListeners(album: Album){
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            if(isLiked){
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(userId)
            }else{
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId,album.id)
            }
        }
    }
}