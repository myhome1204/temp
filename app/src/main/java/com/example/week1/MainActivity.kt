package com.example.week1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.week1.HomeFragment
import com.example.week1.LockerFragment
import com.example.week1.LookFragment
import com.example.week1.R
import com.example.week1.SearchFragment
import com.example.week1.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var song : Song
    private var gson :Gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Week1)
        setContentView(binding.root)
        val song = Song(binding.mainMiniplayerTitleTv.text.toString(),binding.mainMiniplayerSingerTv.text.toString(),0,60,false,"music_lilac")
        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this@MainActivity,SongActivity::class.java)
            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second",song.second)
            intent.putExtra("playTime",song.playTime)
            intent.putExtra("isPlaying",song.isPlaying)
            intent.putExtra("music",song.music)
            startActivity(intent)
//           startActivity(Intent(this@MainActivity,SongActivity::class.java))
        }
        initBottomNavigation()

        Log.d("testt","노래의 아이디는 : ${song.title} ${song.singer}")
    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
    private fun setMiniPlayer(song : Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second*100000)/song.playTime
    }
    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData",null)
        song = if(songJson == null){
            Song("라일락","아이유(IU)",0,60,false,"music_lilac")
        } else{
            gson.fromJson(songJson,Song::class.java)
        }
        setMiniPlayer(song)
    }
}