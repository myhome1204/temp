package com.example.week1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.week1.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    lateinit var song: Song
    lateinit var timer:Timer
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

        initSong()
        setPlayer(song)
    }

    fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying=isPlaying
        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE

        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second",0)!!,
                intent.getIntExtra("playTime",0)!!,
                intent.getBooleanExtra("isPlaying",false)!!
            )
        }
        startTimer()
    }
    private fun setPlayer(song : Song){
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second/60,song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime/60,song.playTime%60)
        binding.songProgressSb.progress = (song.second*1000 / song.playTime)
        setPlayerStatus(song.isPlaying)

    }
    private fun startTimer(){
        timer = Timer(song.playTime,song.isPlaying)
        timer.start()
    }
    inner class Timer(private val playTime : Int , var isPlaying : Boolean = true):Thread(){
        private var second : Int = 0
        private var mills:Float = 0f
        override fun run() {
            super.run()
            try {
                while (true){
                    if(second >=playTime){
                        break
                    }
                    if(isPlaying){
                        sleep(50)
                        mills+=50
                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills/playTime)*100).toInt()
                        }
                        if(mills%1000  == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d",second/60,second%60)
                            }
                            second++
                        }
                    }
                }
            }catch (e : InterruptedException){
                Log.d("testt","쓰레드사망")
            }


        }
    }
}