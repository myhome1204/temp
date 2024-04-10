package com.example.week1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.week1.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySongBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.songDownIb.setOnClickListener {
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }
    }
    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE

        }else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }
}