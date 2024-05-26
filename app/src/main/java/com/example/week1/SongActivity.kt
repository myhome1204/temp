package com.example.week1


import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.week1.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    lateinit var timer:Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson : Gson = Gson()
    var songs  = arrayListOf<Song>()
    val songDB = SongDatabase.getInstance(this)!!
    var nowPos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySongBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListener()
    }
    private fun initClickListener(){
        binding.songDownIb.setOnClickListener {
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songNextIv.setOnClickListener {
            moveSong(1)
        }
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }
    }
    private fun moveSong(direct : Int){
        nowPos +=direct
        if(nowPos + direct <0){
            nowPos = songs.size-1
        }
        if(nowPos + direct >=songs.size){
            nowPos = 0
        }
        timer.interrupt()
        startTimer()
        mediaPlayer?.release()
        mediaPlayer =null
        setPlayer(songs[nowPos])

    }
    fun setPlayerStatus(isPlaying: Boolean) {
        songs[nowPos].isPlaying = isPlaying
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
//        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
//            song = Song(
//                intent.getStringExtra("title")!!,
//                intent.getStringExtra("singer")!!,
//                intent.getIntExtra("second",0)!!,
//                intent.getIntExtra("playTime",0)!!,
//                intent.getBooleanExtra("isPlaying",false)!!,
//                intent.getStringExtra("music")!!
//
//            )
//        }
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)
        nowPos = getPlayingSongPosition(songId)
        startTimer()
        setPlayer(songs[nowPos])
    }
    private fun getPlayingSongPosition(songId :Int) : Int{
        for(i in 0 until songs.size){
            if(songs[i].id == songId){
                return i
            }
        }
        return 0
    }
    private fun initPlayList(){
        songs.addAll(songDB.songDao().getSongs())
    }
    private fun setPlayer(song : Song){
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.title
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second/60,song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime/60,song.playTime%60)
        binding.songProgressSb.progress = (song.second*1000 / song.playTime)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        val music = resources.getIdentifier(song.music,"raw",this.packageName)
        mediaPlayer = MediaPlayer.create(this,music)
        setPlayerStatus(song.isPlaying)
    }
    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)
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
        songs[nowPos].second = ((binding.songProgressSb.progress*songs[nowPos].playTime)/100)/1000

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
//        val songJson = gson.toJson(songs[nowPos])
        editor.putInt("songId",songs[nowPos].id)
        editor.apply()

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer =null
    }
}