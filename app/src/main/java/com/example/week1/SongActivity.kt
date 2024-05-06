package com.example.week1

import android.content.Intent

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.week1.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    lateinit var song: Song
    lateinit var timer:Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson : Gson = Gson()
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
            mediaPlayer?.start()
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer!!.isPlaying==true){
                mediaPlayer?.stop()
            }
        }
    }

    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second",0)!!,
                intent.getIntExtra("playTime",0)!!,
                intent.getBooleanExtra("isPlaying",false)!!,
                intent.getStringExtra("music")!!

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
        val music = resources.getIdentifier(song.music,"raw",this.packageName)
        mediaPlayer = MediaPlayer.create(this,music)
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

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songProgressSb.progress*song.playTime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val songJson = gson.toJson(song)
        editor.putString("songData",songJson)
        editor.apply()

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
    }
}