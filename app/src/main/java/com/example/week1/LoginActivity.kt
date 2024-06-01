package com.example.week1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.week1.databinding.ActivityLoginBinding
import com.example.week1.databinding.ActivitySignUpBinding
import com.example.week1.databinding.ActivitySongBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
        }
        binding.loginSignInBtn.setOnClickListener {
            login()
        }
    }

    private fun login(){
        if(binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this,"이메일형식이 잘못됐습니다", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.loginPasswordEt.text.toString().isEmpty() ){
            Toast.makeText(this,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            return
        }
        val email :String = binding.loginIdEt.text.toString()+"@"+binding.loginDirectInputEt.text.toString()
        val password : String  =  binding.loginPasswordEt.text.toString()

        var songDB = SongDatabase.getInstance(this)!!

        val user =  songDB.userDao().getUser(email,password)
        user?.let {
            Log.d("testt","user id ${user.id}")
            saveJwt(user.id)
            startMainActivity()
        }
        Toast.makeText(this,"회원정보가 존재하지않습니다",Toast.LENGTH_SHORT).show()

    }

    private fun saveJwt(jwt : Int){
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putInt("jwt",jwt)
        editor.apply()
    }

    private fun startMainActivity(){
        val intent = Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
    }
}