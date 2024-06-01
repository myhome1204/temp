package com.example.week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.week1.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signUpSignUpBtn.setOnClickListener{
            signUp()
            finish()
        }
    }

    private fun getUser() : User{
        val email :String = binding.signUpIdEt.text.toString()+"@"+binding.signUpDirectInputEt.text.toString()
        val password : String  =  binding.signUpPasswordEt.text.toString()

        return User(email,password)
    }

    private fun signUp() {
        if(binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this,"이메일형식이 잘못됐습니다",Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.signUpPasswordEt.text.toString() !=  binding.signUpPasswordCheckEt.text.toString()){
            Toast.makeText(this,"이메일형식이 잘못됐습니다",Toast.LENGTH_SHORT).show()
            return
        }

        val userDB = SongDatabase.getInstance(this)!!
        userDB.userDao().insert(getUser())

        val user  = userDB.userDao().getUsers()
        Log.d("testt",user.toString())
    }
}